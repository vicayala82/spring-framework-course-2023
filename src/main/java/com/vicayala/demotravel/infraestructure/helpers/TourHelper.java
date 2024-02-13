package com.vicayala.demotravel.infraestructure.helpers;

import com.vicayala.demotravel.domain.entities.jpa.CustomerEntity;
import com.vicayala.demotravel.domain.entities.jpa.FlyEntity;
import com.vicayala.demotravel.domain.entities.jpa.HotelEntity;
import com.vicayala.demotravel.domain.entities.jpa.ReservationEntity;
import com.vicayala.demotravel.domain.entities.jpa.TicketEntity;
import com.vicayala.demotravel.domain.repositories.jpa.ReservationRepository;
import com.vicayala.demotravel.domain.repositories.jpa.TicketRepository;
import com.vicayala.demotravel.util.BestTravelUtil;
import com.vicayala.demotravel.util.ServiceConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.vicayala.demotravel.util.ServiceConstants.CHARGE_PRICE_PERCENTAGE;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    public Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer){
        var response = new HashSet<TicketEntity>(flights.size());
        flights.forEach(fly -> {
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
            response.add(this.ticketRepository.save(ticketToPersist));
        });
        return response;
    }

    public Set<ReservationEntity> createReservations(Map<HotelEntity, Long> hotels
            , CustomerEntity customer){
        var response = new HashSet<ReservationEntity>(hotels.size());
        hotels.forEach((hotel, totalDays) ->{
            var reservationToPersist = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .dateTimeReservation(LocalDateTime.now())
                    .totalDays(totalDays)
                    .dateStart(LocalDate.now())
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .hotel(hotel)
                    .price(hotel.getPrice().add(hotel.getPrice()
                            .multiply(ServiceConstants.CHARGE_PRICE_PERCENTAGE)))
                    .customer(customer)
                    .build();
            response.add(this.reservationRepository.save(reservationToPersist));
        });
        return response;
    }

    public TicketEntity createTicket(FlyEntity fly, CustomerEntity customer){
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
        return this.ticketRepository.save(ticketToPersist);
    }

    public ReservationEntity createReservation(HotelEntity hotel, CustomerEntity customer, Integer totalDays){
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .hotel(hotel)
                .price(hotel.getPrice().add(hotel.getPrice()
                        .multiply(ServiceConstants.CHARGE_PRICE_PERCENTAGE)))
                .customer(customer)
                .build();
        return this.reservationRepository.save(reservationToPersist);
    }

}
