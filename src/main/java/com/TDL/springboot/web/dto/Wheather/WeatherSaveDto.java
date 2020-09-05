package com.TDL.springboot.web.dto.Wheather;

import com.TDL.springboot.domain.day.Day;
import com.TDL.springboot.domain.weather.Weather;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherSaveDto {
    String day;
    String imgURL;

    @Builder
    public WeatherSaveDto(
            String day,
            String imgURL) {
        this.day = day;
        this.imgURL = imgURL;
    }

    public Weather toDocument() {
        return Weather.builder()
                .day(day)
                .imgURL(imgURL)
                .build();
    }
}
