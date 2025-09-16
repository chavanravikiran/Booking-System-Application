package com.booking.jwt.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {
	
    private Long resourceId;
    private BigDecimal price;
    private Instant startTime;
    private Instant endTime;
    
}
