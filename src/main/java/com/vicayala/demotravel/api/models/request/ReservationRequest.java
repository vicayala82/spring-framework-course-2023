package com.vicayala.demotravel.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @Size(min = 18, max = 20, message = "The size should be between 18 and 20 characters")
    @NotBlank(message = "Client Id is mandatory")
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("hotel_id")
    @Positive
    @NotNull(message = "Hotel Id is mandatory")
    private Long hotelId;
    @JsonProperty("total_days")
    @Positive
    @Min(value = 1, message = "Min one dayus to make reservation")
    @Max(value = 30, message = "Max 30 days to make reservation")
    @NotNull(message = "total days is mandatory")
    private Integer totalDays;
    @JsonProperty("email")
    //@Pattern(regexp = "^(.+)@(.+)$")
    @Email(message = "Invalid Email")
    private String email;
}
