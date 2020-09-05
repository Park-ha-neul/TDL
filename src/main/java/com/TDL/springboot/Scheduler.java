package com.TDL.springboot;

import com.TDL.springboot.domain.weather.Weather;
import com.TDL.springboot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
@Component
public class Scheduler {
    private final WeatherService weatherService;
    @Scheduled(cron = "0 0 3 * * *")
    public void craw() throws IOException {
        int result = weatherService.weatherMain();
        log.info(result);
    }
}
