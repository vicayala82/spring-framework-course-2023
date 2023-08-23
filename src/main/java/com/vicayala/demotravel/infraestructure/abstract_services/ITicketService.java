package com.vicayala.demotravel.infraestructure.abstract_services;

import com.vicayala.demotravel.api.request.TicketRequest;
import com.vicayala.demotravel.api.response.TicketResponse;

import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{
}
