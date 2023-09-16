package com.vicayala.demotravel.api.models.response;

import com.vicayala.demotravel.domain.entities.FlyEntity;
import com.vicayala.demotravel.util.enums.AeroLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlyResponse implements Serializable {

    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    private String originName;
    private String destinyName;
    private BigDecimal price;
    private AeroLine aeroLine;

    public static FlyResponse entityToResponse(FlyEntity flyEntity){
        FlyResponse flyResponse = new FlyResponse();
        BeanUtils.copyProperties(flyEntity, flyResponse);
        return flyResponse;
    }
}
