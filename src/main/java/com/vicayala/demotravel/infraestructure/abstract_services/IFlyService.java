package com.vicayala.demotravel.infraestructure.abstract_services;

import com.vicayala.demotravel.api.models.response.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse> {

    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
