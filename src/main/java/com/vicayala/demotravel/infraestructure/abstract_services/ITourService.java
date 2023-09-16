package com.vicayala.demotravel.infraestructure.abstract_services;

import com.vicayala.demotravel.api.models.request.TourRequest;
import com.vicayala.demotravel.api.models.request.TourResponse;

import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourResponse, Long>{

    void removeTicket(Long tourId, UUID ticketId);
    UUID addTicket(Long tourId, Long flyId);
    void removeReservation(Long tourId, UUID reservationId);
    UUID addReservation(Long tourId, Long reservationId, Integer totalDays);
}
