package com.uber.booking.dto;

import com.uber.common.entities.Driver;
import com.uber.common.entities.ExactLocation;
import com.uber.common.entities.Rider;
import com.uber.common.utils.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingDTO {
    private BookingStatus bookingStatus;
    private Date startTime;
    private Date endTime;
    private Long totalDistance;
    private Driver driver;
    private String riderId;
    private ExactLocation startLocation;
    private ExactLocation endLocation;
}
