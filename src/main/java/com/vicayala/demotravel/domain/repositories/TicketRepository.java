package com.vicayala.demotravel.domain.repositories;

import com.vicayala.demotravel.domain.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.UUID;

public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {

}
