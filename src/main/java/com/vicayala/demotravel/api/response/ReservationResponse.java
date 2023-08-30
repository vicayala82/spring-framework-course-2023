package com.vicayala.demotravel.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vicayala.demotravel.domain.entities.ReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse implements Serializable {

    private UUID id;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTimeReservation;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateStart;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;
    private Long totalDays;
    private BigDecimal price;
    private HotelResponse hotelResponse;

    public static ReservationResponse entityToResponse(ReservationEntity reservationEntity){
        return ReservationResponse.builder()
                .id(reservationEntity.getId())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(reservationEntity.getDateStart())
                .dateEnd(reservationEntity.getDateEnd())
                .price(reservationEntity.getPrice())
                .totalDays(reservationEntity.getTotalDays())
                .hotelResponse(HotelResponse.entityToResponse(reservationEntity.getHotel()))
                .build();
    }

}
