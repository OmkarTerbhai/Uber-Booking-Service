package com.uber.booking.dto;

import com.uber.common.entities.ExactLocation;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndBookingDTO {
    ExactLocation location;
    String driverId;
    String bookingId;
}
