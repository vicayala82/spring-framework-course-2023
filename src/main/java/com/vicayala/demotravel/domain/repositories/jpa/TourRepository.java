package com.vicayala.demotravel.domain.repositories.jpa;

import com.vicayala.demotravel.domain.entities.jpa.TourEntity;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity, Long> {
}
