package com.vicayala.demotravel.domain.repositories.jpa;

import com.vicayala.demotravel.domain.entities.jpa.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}
