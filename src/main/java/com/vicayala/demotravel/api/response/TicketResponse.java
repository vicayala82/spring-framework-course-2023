package com.vicayala.demotravel.api.response;

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
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
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
