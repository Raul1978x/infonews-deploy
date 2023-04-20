package com.egg.noticias.controladores;

//import com.egg.noticias.entidades.WeatherData;
import com.egg.noticias.servicios.WeatherServicio;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class WeatherController {

    @Autowired
    private WeatherServicio weatherService;

//    @GetMapping("/weather")
//    public ResponseEntity<Map<String, String>> getWeatherData(Model model) {
//        WeatherData weatherData = weatherService.getWeatherData();
//
//        double temperature = weatherData.getData().get(0).getTemp();
//        String description = weatherData.getData().get(0).getWeather().getDescription();
//        String iconUrl = weatherData.getData().get(0).getWeather().getIconUrl();
//        Map<String, String> weatherInfo = new HashMap<>();
//        weatherInfo.put("temperature", String.format("%.1f°C", temperature));
//        weatherInfo.put("description", description);
//        weatherInfo.put("iconUrl", iconUrl);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        model.addAttribute("temperature", String.format("%.1f°C", temperature));
//        model.addAttribute("description", description);
//        model.addAttribute("iconUrl", iconUrl);
//        
//        return new ResponseEntity<>(weatherInfo, headers, HttpStatus.OK);
////    }

}
