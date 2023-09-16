package com.vicayala.demotravel.api.models.response;

import com.vicayala.demotravel.domain.entities.HotelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse implements Serializable {

    private Long id;
    private String name;
    private String address;
    private BigDecimal price;
    private Integer rating;

    public static HotelResponse entityToResponse(HotelEntity hotelEntity){
        return HotelResponse.builder()
                .id(hotelEntity.getId())
                .name(hotelEntity.getName())
                .address(hotelEntity.getAddress())
                .price(hotelEntity.getPrice())
                .rating(hotelEntity.getRating())
                .build();
    }
}
