package com.vicayala.demotravel;

import com.vicayala.demotravel.domain.entities.ReservationEntity;
import com.vicayala.demotravel.domain.entities.TicketEntity;
import com.vicayala.demotravel.domain.entities.TourEntity;
import com.vicayala.demotravel.domain.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class Application implements CommandLineRunner {

	private final HotelRepository hotelRepository;
	private final FlyRepository flyRepository;
	private final TicketRepository ticketRepository;
	private final ReservationRepository reservationRepository;
	private final TourRepository tourRepository;
	private final CustomerRepository customerRepository;


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		var fly= flyRepository.findById(15L).get();
		var hotel= hotelRepository.findById(7L).get();
		var ticket= ticketRepository.findById(UUID.fromString("22345678-1234-5678-3235-567812345678")).get();
		var reservation = reservationRepository
				.findById(UUID.fromString("12345678-1234-5678-1234-567812345678")).get();
		var customer = customerRepository.findById("WALA771012HCRGR054").get();
		log.info(String.valueOf(fly));
		log.info(String.valueOf(ticket));
		log.info(String.valueOf(hotel));
		log.info(String.valueOf(reservation));
		log.info(String.valueOf(customer));
		this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(System.out::println);
		System.out.println("Between");
		this.flyRepository.selectBetweenPrice(BigDecimal.valueOf(10), BigDecimal.valueOf(15))
			.forEach(System.out::println);
		System.out.println("Destiny");
		this.flyRepository.selectOriginDestiny("Grecia", "Mexico")
				.forEach(System.out::println);
		var flyT = flyRepository.findByTicketId(UUID.fromString("12345678-1234-5678-2236-567812345678"));
		log.info("Fly  x Ticket : " + flyT);
		hotelRepository.findByPriceLessThan(BigDecimal.valueOf(100)).forEach(System.out::println);
		log.info("Hotel between");
		hotelRepository.findByPriceBetween(BigDecimal.valueOf(80), BigDecimal.valueOf(150))
				.forEach(System.out::println);
		log.info("Hotel x rating");
		hotelRepository.findByRatingGreaterThan(3).forEach(System.out::print);

		var hotelUuid = hotelRepository.findByReservationsId(UUID.fromString("12345678-1234-5678-1234-567812345678"));
		log.info("Hotel x reservation UUID : " + hotelUuid);

		var customerNew = customerRepository.findById("GOTW771012HMRGR087").get();
		log.info("Customer: "+ customerNew);
		var hotelNew = hotelRepository.findById(3L).orElseThrow();
		log.info("Hotel: "+ hotelNew);
		var flyNew = flyRepository.findById(11L).orElseThrow();
		log.info("fly : "+flyNew);
		var tour = TourEntity.builder()
				.customer(customer)
				.build();
		var ticketNew = TicketEntity.builder()
				.id(UUID.randomUUID())
				.price(fly.getPrice().multiply(BigDecimal.TEN))
				.arrivalDate(LocalDate.now())
				.departureDate(LocalDate.now())
				.purchaseDate(LocalDate.now())
				.customer(customerNew)
				.tour(tour)
				.fly(flyNew)
				.build();
		log.info("Ticket : "+ ticketNew);

		var reservationNew = ReservationEntity.builder()
				.id(UUID.randomUUID())
				.dateTimeReservation(LocalDateTime.now())
				.dateEnd(LocalDate.now().plusDays(2))
				.dateStart(LocalDate.now().plusDays(1))
				.hotel(hotelNew)
				.customer(customerNew)
				.tour(tour)
				.totalDays(1)
				.price(hotelNew.getPrice().multiply(BigDecimal.TEN))
				.build();

		tour.addTicket(ticketNew);
		tour.updateTickets();
		tour.addReservation(reservationNew);
		tour.updateReservation();

		var tourSaved = this.tourRepository.save(tour);
		this.tourRepository.deleteById(tourSaved.getId());

	}

}
