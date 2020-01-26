package dev.yoghurt1131.weatherapi.application.controller;

import dev.yoghurt1131.weatherapi.application.exception.ApiCallException;
import dev.yoghurt1131.weatherapi.application.service.WeatherApiService;
import dev.yoghurt1131.weatherapi.domain.CurrentWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/current")
@RestController
public class CurrentWeatherController {

    private final WeatherApiService weatherApiService;

    @Autowired
    public CurrentWeatherController(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @GetMapping("/city")
    public CurrentWeather city(@RequestParam(required = true) String cityName) {
        try {
            CurrentWeather weather = weatherApiService.getCurrentWeather(cityName);
            return weather;
        } catch (ApiCallException e) {
            return null;
        }
    }
}