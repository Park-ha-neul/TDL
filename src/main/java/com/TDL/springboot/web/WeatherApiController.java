package com.TDL.springboot.web;

import com.TDL.springboot.service.WeatherService;
import com.TDL.springboot.web.dto.Wheather.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
@RestController
public class WeatherApiController {
    private final WeatherService weatherService;

    // 강제 크롤링
    @GetMapping("/api/v1/weather")
    public void craw() throws IOException {
        int result = weatherService.weatherMain();
        log.info(result);
    }

    //조회
    @GetMapping("/api/v1/weather/{day}")
    public WeatherResponseDto findByDay(@PathVariable String day) {
        return weatherService.findByDay(day);
    }
}
