package com.yoghurt1131.weatherapi.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Clouds {

    @JsonProperty("all")
    private int cloudness;
}
