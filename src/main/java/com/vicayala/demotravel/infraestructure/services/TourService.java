package com.vicayala.demotravel.infraestructure.services;

import com.vicayala.demotravel.api.models.request.TourRequest;
import com.vicayala.demotravel.api.models.request.TourResponse;
import com.vicayala.demotravel.domain.entities.jpa.FlyEntity;
import com.vicayala.demotravel.domain.entities.jpa.HotelEntity;
import com.vicayala.demotravel.domain.entities.jpa.ReservationEntity;
import com.vicayala.demotravel.domain.entities.jpa.TicketEntity;
import com.vicayala.demotravel.domain.entities.jpa.TourEntity;
import com.vicayala.demotravel.domain.repositories.jpa.CustomerRepository;
import com.vicayala.demotravel.domain.repositories.jpa.FlyRepository;
import com.vicayala.demotravel.domain.repositories.jpa.HotelRepository;
import com.vicayala.demotravel.domain.repositories.jpa.TourRepository;
import com.vicayala.demotravel.infraestructure.abstract_services.ITourService;
import com.vicayala.demotravel.infraestructure.helpers.BlackListHelper;
import com.vicayala.demotravel.infraestructure.helpers.CustomerHelper;
import com.vicayala.demotravel.infraestructure.helpers.EmailHelper;
import com.vicayala.demotravel.infraestructure.helpers.TourHelper;
import com.vicayala.demotravel.util.enums.Tables;
import com.vicayala.demotravel.util.exceptions.IdNotFoundExceptions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final EmailHelper emailHelper;

    @Override
    public TourResponse create(TourRequest request) {
        blackListHelper.isInCustomerBlackList(request.getCustomerId());
        var customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.customer.name()));
        var flights = new HashSet<FlyEntity>();
        var hotels = new HashMap<HotelEntity, Long>();
        request.getFlights().forEach(fly -> flights
                    .add(this.flyRepository.findById(fly.getId())
                        .orElseThrow(()-> new IdNotFoundExceptions(Tables.fly.name())))
                );
        request.getHotels().forEach(hotel -> hotels.put(this.hotelRepository
            .findById(hotel.getId()).orElseThrow(), hotel.getTotalDays())
        );
        var tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTickets(flights,customer))
                .reservations(this.tourHelper.createReservations(hotels,customer))
                .customer(customer)
                .build();
        var tourSaved = this.tourRepository.save(tourToSave);
        this.customerHelper.increase(customer.getDni(), TourService.class);
        if(Objects.nonNull(request.getEmail())){
            this.emailHelper.sendMail(request.getEmail(),
                    customer.getFullName(), Tables.tour.name());
        }
        return TourResponse.builder()
                .reservationIds(tourSaved.getReservations().stream()
                        .map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketsIds(tourSaved.getTickets().stream()
                        .map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long id) {
        var tourFromDb = this.tourRepository.findById(id)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.tour.name()));
        return TourResponse.builder()
                .reservationIds(tourFromDb.getReservations().stream()
                        .map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketsIds(tourFromDb.getTickets().stream()
                        .map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourFromDb.getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        var tourToDelete = this.tourRepository.findById(id)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.tour.name()));
        this.tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(Long tourId, UUID ticketId) {
        var tourUpdate = this.tourRepository.findById(tourId)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.tour.name()));
        tourUpdate.removeTicket(ticketId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long tourId, Long flyId) {
        var tourUpdate = this.tourRepository.findById(tourId)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.tour.name()));
        var fly = this.flyRepository.findById(flyId)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.fly.name()));
        var ticket = this.tourHelper.createTicket(fly, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);
        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId) {
        var tourUpdate = this.tourRepository.findById(tourId)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.tour.name()));
        tourUpdate.removeReservation(reservationId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addReservation(Long tourId, Long hotelId, Integer totalDays) {
        var tourUpdate = this.tourRepository.findById(tourId)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.tour.name()));
        var hotel = this.hotelRepository.findById(hotelId)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.hotel.name()));
        var reservation = this.tourHelper.createReservation(hotel,tourUpdate.getCustomer(),totalDays);
        tourUpdate.addReservation(reservation);
        this.tourRepository.save(tourUpdate);
        return reservation.getId();
    }

}
