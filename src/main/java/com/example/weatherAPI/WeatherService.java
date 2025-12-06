package com.example.weatherAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate=new RestTemplate(); // used to call external api

    @Value("${weather.api.key}")
    private String apiKey;

    @Cacheable(value="weather",key="#city",unless="#result==null")
    public Map<String,Object> getWeather(String city) {
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + city
                + "?unitGroup=metric"
                + "&key=" + apiKey
                + "&contentType=json"
                + "&include=days"
                + "&elements=datetime,temp,tempmax,tempmin,humidity,conditions,icon";
        return restTemplate.getForObject(url,Map.class);
    }

}
