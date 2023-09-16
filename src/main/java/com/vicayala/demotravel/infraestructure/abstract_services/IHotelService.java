package com.vicayala.demotravel.infraestructure.abstract_services;

import com.vicayala.demotravel.api.models.response.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse>{

    Set<HotelResponse> readByRating(Integer rating);
}
