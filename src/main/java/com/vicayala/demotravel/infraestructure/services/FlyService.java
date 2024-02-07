package com.vicayala.demotravel.infraestructure.services;

import com.vicayala.demotravel.api.models.response.FlyResponse;
import com.vicayala.demotravel.domain.repositories.FlyRepository;
import com.vicayala.demotravel.infraestructure.abstract_services.IFlyService;
import com.vicayala.demotravel.util.ServiceConstants;
import com.vicayala.demotravel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {

    private final FlyRepository flyRepository;
    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page,size);
            case LOWER -> pageRequest = PageRequest.of(page,size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page,size,Sort.by(FIELD_BY_SORT).descending());
        }
        return this.flyRepository.findAll(pageRequest).map(FlyResponse::entityToResponse);
    }

    @Override
    @Cacheable(value = ServiceConstants.FLY_CACHE_NAME)
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return flyRepository.selectLessPrice(price)
                .stream()
                .map(FlyResponse::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = ServiceConstants.FLY_CACHE_NAME)
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return flyRepository.selectBetweenPrice(min, max)
                .stream()
                .map(FlyResponse::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = ServiceConstants.FLY_CACHE_NAME)
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return flyRepository.selectOriginDestiny(origin, destiny)
                .stream()
                .map(FlyResponse::entityToResponse)
                .collect(Collectors.toSet());
    }
}
