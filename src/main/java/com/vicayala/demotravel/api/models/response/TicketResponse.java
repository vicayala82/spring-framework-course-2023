package com.vicayala.demotravel.api.models.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vicayala.demotravel.domain.entities.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TicketResponse implements Serializable {

    private UUID id;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;
    private BigDecimal price;
    private FlyResponse flyResponse;

    public static TicketResponse entityToResponse(TicketEntity ticket){
        TicketResponse response = new TicketResponse();
        BeanUtils.copyProperties(ticket, response);
        FlyResponse flyResponse = new FlyResponse();
        BeanUtils.copyProperties(ticket.getFly(), flyResponse);
        response.setFlyResponse(flyResponse);
        return response;
    }
}
