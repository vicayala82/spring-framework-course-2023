package com.vicayala.demotravel.infraestructure.services;

import com.vicayala.demotravel.api.request.TicketRequest;
import com.vicayala.demotravel.api.response.TicketResponse;
import com.vicayala.demotravel.domain.entities.TicketEntity;
import com.vicayala.demotravel.domain.repositories.CustomerRepository;
import com.vicayala.demotravel.domain.repositories.FlyRepository;
import com.vicayala.demotravel.domain.repositories.TicketRepository;
import com.vicayala.demotravel.infraestructure.abstract_services.ITicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

     @Override
    public TicketResponse create(TicketRequest request) {
        var fly = flyRepository.findById(request.getFlyId()).orElseThrow();
        var customer = customerRepository.findById(request.getClientId()).orElseThrow();
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().multiply(BigDecimal.valueOf(0.25)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(LocalDateTime.now())
                .departureDate(LocalDateTime.now())
                .build();
        var ticketPersisted = this.ticketRepository.save(ticketToPersist);
        log.info("Ticket saved with id: {}", ticketPersisted.getId());

        return TicketResponse.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {
        var ticketFromDB = this.ticketRepository.findById(id).orElseThrow();
        return TicketResponse.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        var ticketToUpdate = this.ticketRepository.findById(id).orElseThrow();
        var fly = flyRepository.findById(request.getFlyId()).orElseThrow();
        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(BigDecimal.valueOf(0.25));
        ticketToUpdate.setDepartureDate(LocalDateTime.now());
        ticketToUpdate.setArrivalDate(LocalDateTime.now());
        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);
        return TicketResponse.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = this.ticketRepository.findById(id).orElseThrow();
        this.ticketRepository.deleteById(id);
    }
}
