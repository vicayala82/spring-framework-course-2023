package com.vicayala.demotravel.api.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourRequest implements Serializable {

    @Positive
    @NotNull(message = "Hotel Id is mandatory")
    private String customerId;
    private Set<TourFlyRequest> flights;
    private Set<TourHotelRequest> hotels;
    @Email(message = "Invalid Email")
    private String email;
}
