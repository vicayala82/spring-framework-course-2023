package com.vicayala.demotravel.infraestructure.services;

import com.vicayala.demotravel.api.models.response.HotelResponse;
import com.vicayala.demotravel.domain.repositories.jpa.HotelRepository;
import com.vicayala.demotravel.infraestructure.abstract_services.IHotelService;
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
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;
    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page,size);
            case LOWER -> pageRequest = PageRequest.of(page,size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page,size,Sort.by(FIELD_BY_SORT).descending());
        }
        return this.hotelRepository.findAll(pageRequest).map(HotelResponse::entityToResponse);
    }

    @Override
    @Cacheable(value = ServiceConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return hotelRepository.findByPriceLessThan(price)
                .stream()
                .map(HotelResponse::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = ServiceConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return hotelRepository.findByPriceBetween(min, max)
                .stream()
                .map(HotelResponse::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = ServiceConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readByRating(Integer rating) {
        if(rating > 4) rating = 4;
        if(rating < 1) rating = 1;
        return hotelRepository.findByRatingGreaterThan(rating)
                .stream()
                .map(HotelResponse::entityToResponse)
                .collect(Collectors.toSet());
    }
}
