package com.TDL.springboot.web.dto.Wheather;

import com.TDL.springboot.domain.weather.Weather;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherResponseDto {
    private String id;
    private String day;
    private String imgURL;

    public WeatherResponseDto(Weather entity) {
        this.id = entity.getId();
        this.day = entity.getDay();
        this.imgURL = entity.getImgURL();
    }
}
