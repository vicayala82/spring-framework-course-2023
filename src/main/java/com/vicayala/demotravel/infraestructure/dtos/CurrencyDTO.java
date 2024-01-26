package com.vicayala.demotravel.infraestructure.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO implements Serializable {

    @JsonProperty("date")
    private LocalDate exchangeDate;
    private Map<Currency, BigDecimal> rates;
}
