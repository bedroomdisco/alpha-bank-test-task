package com.example.richbroken.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OpenExchangeRates {
    private Map<String, Float> rateMap;

    public OpenExchangeRates(Map<String, Float> rateMap) {
        if(rateMap != null) {
            this.rateMap = rateMap;
        } else {
            this.rateMap = new HashMap<>();
        }
    }

    public Map<String, Float> getRates() {
        return rateMap;
    }

    public Optional<Float> getRate(String currency) {
        if(currency != null && rateMap.containsKey(currency)) {
            return Optional.ofNullable(rateMap.get(currency));
        }
        return Optional.empty();
    }
}
