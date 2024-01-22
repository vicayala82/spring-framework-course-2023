package com.vicayala.demotravel.api.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourFlyRequest implements Serializable {

    @Positive
    @NotNull(message = "Hotel Id is mandatory")
    private long id;
}
