package com.vicayala.demotravel.domain.repositories;

import com.vicayala.demotravel.domain.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}
