package com.vicayala.demotravel.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest implements Serializable {

    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("hotel_id")
    private Long hotelId;
}
