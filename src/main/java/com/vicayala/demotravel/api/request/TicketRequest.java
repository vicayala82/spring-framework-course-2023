package com.vicayala.demotravel.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long flyId;
}
