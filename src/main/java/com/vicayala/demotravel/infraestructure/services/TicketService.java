package com.vicayala.demotravel.infraestructure.services;

import com.vicayala.demotravel.api.models.request.TicketRequest;
import com.vicayala.demotravel.api.models.response.TicketResponse;
import com.vicayala.demotravel.domain.entities.TicketEntity;
import com.vicayala.demotravel.domain.entities.TourEntity;
import com.vicayala.demotravel.domain.repositories.CustomerRepository;
import com.vicayala.demotravel.domain.repositories.FlyRepository;
import com.vicayala.demotravel.domain.repositories.TicketRepository;
import com.vicayala.demotravel.infraestructure.abstract_services.ITicketService;
import com.vicayala.demotravel.infraestructure.helpers.BlackListHelper;
import com.vicayala.demotravel.infraestructure.helpers.CustomerHelper;
import com.vicayala.demotravel.util.BestTravelUtil;
import com.vicayala.demotravel.util.enums.Tables;
import com.vicayala.demotravel.util.exceptions.IdNotFoundExceptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.vicayala.demotravel.util.ServiceConstants.CHARGE_PRICE_PERCENTAGE;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;

     @Override
    public TicketResponse create(TicketRequest request) {
        blackListHelper.isInCustomerBlackList(request.getClientId());
        var fly = flyRepository.findById(request.getFlyId()).orElseThrow();
        var customer = customerRepository.findById(request.getClientId()).orElseThrow();
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice()
                        .multiply(CHARGE_PRICE_PERCENTAGE)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(BestTravelUtil.getRandomLatter())
                .departureDate(BestTravelUtil.getRandomSoon())
                .build();
        var ticketPersisted = this.ticketRepository.save(ticketToPersist);
        log.info("Ticket saved with id: {}", ticketPersisted.getId());
        this.customerHelper.increase(customer.getDni(), TicketService.class);
        return TicketResponse.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {
        var ticketFromDB = this.ticketRepository.findById(id)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.ticket.name()));
        return TicketResponse.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        var ticketToUpdate = this.ticketRepository.findById(id)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.ticket.name()));
        var fly = flyRepository.findById(request.getFlyId()).orElseThrow();
        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice()
                .multiply(CHARGE_PRICE_PERCENTAGE)));
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLatter());
        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);
        return TicketResponse.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = this.ticketRepository.findById(id)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.ticket.name()));
        this.ticketRepository.deleteById(ticketToDelete.getId());
    }

    @Override
    public BigDecimal findPrice(Long idFly) {
         var fly = this.flyRepository.findById(idFly)
             .orElseThrow(()-> new IdNotFoundExceptions(Tables.fly.name()));
        return fly.getPrice().add(fly.getPrice().multiply(CHARGE_PRICE_PERCENTAGE));
    }
}
