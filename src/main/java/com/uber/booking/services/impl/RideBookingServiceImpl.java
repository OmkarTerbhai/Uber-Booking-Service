package com.uber.booking.services.impl;

import com.uber.booking.dto.CreateBookingDTO;
import com.uber.booking.dto.NearbyDriversDto;
import com.uber.booking.dto.SaveDriverLocationDto;
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

    private RestTemplate restTemplate;

    public RideBookingServiceImpl() {
        this.restTemplate = new RestTemplate();
    }
    @Override
    public Booking createBooking(CreateBookingDTO dto) {
        Rider r = Rider.builder()
                .phoneNumber("9898989898")
                .name("John")
                .email("john@outlook.com")
                .password("John123")
                .build();

        Driver d = Driver.builder()
                .phoneNumber("9898989898")
                .name("John")
                .licenseNumber("MH12ER6650")
                .aadharCard("557686787983")
                .build();

        this.passengerRepository.save(r);

        Optional<Rider> rider = this.passengerRepository.findById(dto.getRiderId());


        //Find Nearby driver with location :)
        List<SaveDriverLocationDto> driverList = getNearByDrivers(dto.getStartLocation());

        Optional<Driver> driver = this.driverRepository.findById(driverList.get(0).getDriverId());
        Booking booking = Booking
                .builder()
                .rider(rider.get())
                .startLocation(dto.getStartLocation())
                .endLocation(dto.getEndLocation())
                .startTime(new Date(System.currentTimeMillis()))
                .endTime(new Date(System.currentTimeMillis() + 300000L))
                .driver(driver.get())
                .build();

        this.bookingRepository.save(booking);


        return booking;
    }

    private List<SaveDriverLocationDto> getNearByDrivers(ExactLocation startLocation) {
        NearbyDriversDto  nearbyDriversDto = NearbyDriversDto
                .builder()
                .longitude(startLocation.getLongitude())
                .latitude(startLocation.getLatitude())
                .build();

        ResponseEntity<SaveDriverLocationDto[]> res = this.restTemplate.postForEntity(BookingConstants.NEARBY_DRIVER_API_URL, nearbyDriversDto, SaveDriverLocationDto[].class);

        List<SaveDriverLocationDto> driverLocations = Arrays.asList(res.getBody());
            driverLocations.forEach(driverLocationDto -> {
                System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
            });


        return driverLocations;
    }
}
