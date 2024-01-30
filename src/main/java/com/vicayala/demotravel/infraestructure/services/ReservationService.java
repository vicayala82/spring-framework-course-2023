package com.vicayala.demotravel.infraestructure.services;

import com.vicayala.demotravel.api.models.request.ReservationRequest;
import com.vicayala.demotravel.api.models.response.ReservationResponse;
import com.vicayala.demotravel.domain.entities.ReservationEntity;
import com.vicayala.demotravel.domain.repositories.CustomerRepository;
import com.vicayala.demotravel.domain.repositories.HotelRepository;
import com.vicayala.demotravel.domain.repositories.ReservationRepository;
import com.vicayala.demotravel.infraestructure.abstract_services.IReservationService;
import com.vicayala.demotravel.infraestructure.helpers.ApiCurrencyConnectorHelper;
import com.vicayala.demotravel.infraestructure.helpers.BlackListHelper;
import com.vicayala.demotravel.infraestructure.helpers.CustomerHelper;
import com.vicayala.demotravel.infraestructure.helpers.EmailHelper;
import com.vicayala.demotravel.util.ServiceConstants;
import com.vicayala.demotravel.util.enums.Tables;
import com.vicayala.demotravel.util.exceptions.IdNotFoundExceptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final ApiCurrencyConnectorHelper apiCurrencyConnectorHelper;
    private final EmailHelper emailHelper;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        blackListHelper.isInCustomerBlackList(request.getClientId());
        var hotel = hotelRepository.findById(request.getHotelId())
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.hotel.name()));
        var customer = customerRepository.findById(request.getClientId())
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.customer.name()));
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(5))
                .hotel(hotel)
                .price(hotel.getPrice().add(hotel.getPrice()
                    .multiply(ServiceConstants.CHARGE_PRICE_PERCENTAGE)))
                .customer(customer)
                .build();
        reservationToPersist.setTotalDays(ChronoUnit.DAYS.between(reservationToPersist.getDateStart()
                ,reservationToPersist.getDateEnd()));
        var reservationPersisted = ReservationResponse
                .entityToResponse(reservationRepository.save(reservationToPersist));
        this.customerHelper.increase(customer.getDni(), ReservationService.class);
        if(Objects.nonNull(request.getEmail())){
            this.emailHelper.sendMail(request.getEmail(),
                customer.getFullName(), Tables.reservation.name());
        }
        return reservationPersisted;
    }

    @Override
    public ReservationResponse read(UUID id) {
        var reservationFromDB = reservationRepository.findById(id)
            .orElseThrow(()-> new IdNotFoundExceptions(Tables.reservation.name()));
        return ReservationResponse.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID id) {
        var hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(()-> new IdNotFoundExceptions(Tables.hotel.name()));
        var customer = customerRepository.findById(request.getClientId())
                .orElseThrow(()-> new IdNotFoundExceptions(Tables.customer.name()));
        var reservationToUpdate = reservationRepository.findById(id)
                .orElseThrow(()-> new IdNotFoundExceptions(Tables.reservation.name()));
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(5));
        reservationToUpdate.setTotalDays(ChronoUnit.DAYS.between(reservationToUpdate.getDateStart()
                ,reservationToUpdate.getDateEnd()));
        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setCustomer(customer);
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice()
                .multiply(ServiceConstants.CHARGE_PRICE_PERCENTAGE)));
        var reservationUpdated = reservationRepository.save(reservationToUpdate);
        return ReservationResponse.entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID id) {
        var reservationToDelete = reservationRepository.findById(id)
                .orElseThrow(()-> new IdNotFoundExceptions(Tables.reservation.name()));
        reservationRepository.deleteById(reservationToDelete.getId());
    }

    @Override
    public BigDecimal findPrice(Long hotelId, Currency currency){
        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new IdNotFoundExceptions(Tables.hotel.name()));
        var priceInDollars = hotel.getPrice().add(hotel.getPrice()
                .multiply(ServiceConstants.CHARGE_PRICE_PERCENTAGE));
        if(currency.equals(Currency.getInstance("USD"))) {
            return priceInDollars;
        }
        var currencyDTO = this.apiCurrencyConnectorHelper.getCurrency(currency);
        log.info("API Currency in {}, response{}",
            currencyDTO.getExchangeDate().toString(), currencyDTO.getRates());
        return priceInDollars.multiply(currencyDTO.getRates().get(currency));
    }
}
