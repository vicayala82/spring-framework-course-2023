package com.vicayala.demotravel.domain.repositories;

import com.vicayala.demotravel.domain.entities.TourEntity;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity, Long> {
}
