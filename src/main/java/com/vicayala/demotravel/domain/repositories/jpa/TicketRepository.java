package com.vicayala.demotravel.domain.repositories.jpa;

import com.vicayala.demotravel.domain.entities.jpa.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {

}
