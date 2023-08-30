package com.vicayala.demotravel.infraestructure.abstract_services;

import com.vicayala.demotravel.api.request.ReservationRequest;
import com.vicayala.demotravel.api.response.ReservationResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID>{
    BigDecimal findPrice(Long hotelId);
}