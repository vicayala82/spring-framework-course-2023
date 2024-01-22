package com.vicayala.demotravel.api.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourResponse implements Serializable {

    @Positive
    @NotNull(message = "Hotel Id is mandatory")
    private Long id;
    private Set<UUID> ticketsIds;
    private Set<UUID> reservationIds;

}
