package com.vicayala.demotravel.infraestructure.abstract_services;

import com.vicayala.demotravel.api.models.request.TicketRequest;
import com.vicayala.demotravel.api.models.response.TicketResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{

    BigDecimal findPrice(Long idFly);
}
