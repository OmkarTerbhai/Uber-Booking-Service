package com.uber.booking.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingDTO {
    private String bookingId;
    private String driverId;
}
