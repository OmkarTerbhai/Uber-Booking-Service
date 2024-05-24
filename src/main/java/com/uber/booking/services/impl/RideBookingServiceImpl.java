package com.uber.booking.services.impl;

import com.uber.booking.apispec.DriverFinderServiceSpec;
import com.uber.booking.apispec.LocationServiceAPISpec;
import com.uber.booking.dto.*;
import com.uber.booking.repositories.BookingRepository;
import com.uber.booking.repositories.DriverRepository;
import com.uber.booking.repositories.PassengerRepository;
import com.uber.booking.services.BookingService;
import com.uber.booking.utils.BookingConstants;


import com.uber.common.entities.Booking;
import com.uber.common.entities.Driver;
import com.uber.common.entities.ExactLocation;
import com.uber.common.entities.Rider;
import com.uber.common.utils.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.awt.print.Book;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RideBookingServiceImpl implements BookingService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LocationServiceAPISpec locationServiceAPISpec;

    @Autowired
    private DriverFinderServiceSpec driverFinderServiceSpec;

    private RestTemplate restTemplate;

    private List<SaveDriverLocationDto> driverLocations;

    public RideBookingServiceImpl() {
        this.restTemplate = new RestTemplate();
    }
    @Override
    public Booking createBooking(CreateBookingDTO dto) {

        Optional<Rider> rider = this.passengerRepository.findById(dto.getRiderId());



        Booking booking = Booking
                .builder()
                .rider(rider.get())
                .startLocation(dto.getStartLocation())
                .endLocation(dto.getEndLocation())
                .startTime(new Date(System.currentTimeMillis()))
                .endTime(new Date(System.currentTimeMillis() + 300000L))
                .build();

        this.bookingRepository.save(booking);

        //Find Nearby driver with location :)
        getNearByDrivers(dto.getStartLocation(), String.valueOf(booking.getId()));

        return booking;
    }

    @Override
    public Booking updateBooking(UpdateBookingDTO dto) {
        Optional<Booking> b = this.bookingRepository.findById(dto.getBookingId());
        Optional<Driver> d = this.driverRepository.findById(dto.getDriverId());

        Driver driver = d.get();
        driver.setAvailable(false);
        
        Booking booking = b.get();
        booking.setDriver(d.get());
        booking.setBookingStatus(BookingStatus.IN_RIDE);
        booking.setUpdatedAt(new Date(System.currentTimeMillis()));
        this.bookingRepository.save(booking);

        return booking;
    }

    @Override
    public Boolean endBooking(EndBookingDTO dto) {
        Optional<Booking> b = this.bookingRepository.findById(dto.getBookingId());
        Booking booking = b.get();

        Optional<Driver> d = this.driverRepository.findById(dto.getDriverId());
        Driver driver = d.get();

        if(dto.getLocation().getLatitude() == booking.getEndLocation().getLatitude() &&
        dto.getLocation().getLongitude() == booking.getEndLocation().getLongitude()) {
            //Valid End of booking
            booking.setBookingStatus(BookingStatus.COMPLETED);
            driver.setAvailable(true);
        }

        this.bookingRepository.save(booking);
        this.driverRepository.save(driver);

        return true;
    }

    private void getNearByDrivers(ExactLocation startLocation, String bookingId) {
        System.out.println("In getNearByDrivers");
        NearbyDriversDto  nearbyDriversDto = NearbyDriversDto
                .builder()
                .longitude(startLocation.getLongitude())
                .latitude(startLocation.getLatitude())
                .build();

        try {
            Call<SaveDriverLocationDto[]> call = this.locationServiceAPISpec.getNearByDrivers(nearbyDriversDto);

            call.enqueue(new Callback<SaveDriverLocationDto[]>() {
                @Override
                public void onResponse(Call<SaveDriverLocationDto[]> call, Response<SaveDriverLocationDto[]> response) {
                    System.out.println("In getNearByDrivers: response handler");
                    driverLocations = Arrays.asList(response.body());
                    System.out.println("Array of drivers: " + driverLocations.get(0).getDriverId());
                    RequestDriverDTO dto = RequestDriverDTO.builder()
                                    .bookingId(bookingId).driverIds(driverLocations).build();
                    requestRide(dto);
                }

                @Override
                public void onFailure(Call<SaveDriverLocationDto[]> call, Throwable t) {
                    throw new RuntimeException();
                }
            });
        }
        catch (Exception e) {
            System.out.println(e);
        }



    }

    /**
     * Method to request nearby drivers.
     *
     * @param dto
     */
    private void requestRide(RequestDriverDTO dto) {
        Call<Boolean> call = this.driverFinderServiceSpec.requestDriver(dto);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("Inside ride requested response handler" + response.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {

            }
        });
    }
}
