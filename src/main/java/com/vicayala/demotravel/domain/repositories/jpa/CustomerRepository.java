package com.vicayala.demotravel.domain.repositories.jpa;

import com.vicayala.demotravel.domain.entities.jpa.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}
