package com.booking.jwt.dto;

import java.math.BigDecimal;
import java.time.Instant;
import com.booking.jwt.entity.Reservation;
import com.booking.jwt.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
	
    private Long reservationId;
    private Long resourceId;
    private Long userId;
    private ReservationStatus status;
    private BigDecimal price;
    private Instant startTime;
    private Instant endTime;
    private Instant createdAt;
    private Instant updatedAt;
    
    public static ReservationDTO fromEntity(Reservation r) {
        return ReservationDTO.builder()
                .reservationId(r.getReservationId())
                .resourceId(r.getResource().getResourceId())
                .userId(r.getUser().getUserId())
                .status(r.getStatus())
                .price(r.getPrice())
                .startTime(r.getStartTime())
                .endTime(r.getEndTime())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }
    
    public static ReservationDTO toDto(Reservation r) {
        return ReservationDTO.builder()
                .reservationId(r.getReservationId())
                .resourceId(r.getResource().getResourceId())
                .userId(r.getUser().getUserId())
                .status(r.getStatus())
                .price(r.getPrice())
                .startTime(r.getStartTime())
                .endTime(r.getEndTime())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }
   
}