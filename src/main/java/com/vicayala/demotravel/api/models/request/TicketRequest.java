package com.vicayala.demotravel.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TicketRequest implements Serializable {

    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("fly_id")
    @Positive
    @NotNull(message = "Fly Id is mandatory")
    private Long flyId;
    @Email(message = "Invalid Email")
    private String email;
}
