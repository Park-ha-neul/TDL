package com.TDL.springboot.service;

import com.TDL.springboot.domain.weather.Weather;
import com.TDL.springboot.domain.weather.WeatherRepository;
import com.TDL.springboot.web.dto.Wheather.WeatherResponseDto;
import com.TDL.springboot.web.dto.Wheather.WeatherSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    // Controller
    @Transactional
    public WeatherResponseDto findByDay(String day) {
        Weather weather = weatherRepository.findByDay(day);
        return new WeatherResponseDto(weather);
    }

    // Scheduler
    public int weatherMain() throws IOException {
        Boolean success = false;
        String URL = "https://weather.naver.com/period/weeklyFcast.nhn";

        Document doc = Jsoup.connect(URL).get();

        ArrayList<String> days = new ArrayList<>();
        ArrayList<String> weathers = new ArrayList<>();

        Elements dayElements = doc.select("th[scope='colgroup']");
        for(Element day : dayElements) {
            String dayText = day.text().split("\\.")[1];
            if(dayText.length() == 1) {
                dayText = "0" + dayText;
            }
            days.add(dayText);
        }

        Elements weatherElements = doc.select("table.tbl_wk tbody tr:nth-child(2) img");
        int size = weatherElements.size();
        for(int i = 0; i < size; i++) {
            weathers.add(weatherElements.get(i).attr("src"));
            i++;
        }

        if(days.size() == 6 && weathers.size() == 6) {
            ArrayList<WeatherSaveDto> weatherSaveDtoList = new ArrayList<>();
            for(int i = 0; i < 6; i++) {
                WeatherSaveDto weatherSaveDto = new WeatherSaveDto(days.get(i), weathers.get(i));
                weatherSaveDtoList.add(weatherSaveDto);
            }

            success = branchWeather(weatherSaveDtoList);
        }

        return success ? 1 : -1;
    }

    @Transactional
    public String saveWeather(WeatherSaveDto weatherSaveDto) {
        return weatherRepository.save(weatherSaveDto.toDocument()).getId();
    }

    @Transactional
    public String updateWeather(WeatherSaveDto weatherSaveDto) {
        Weather weather = weatherRepository.findByDay(weatherSaveDto.getDay());
        weather.update(weatherSaveDto.getImgURL());
        return weatherRepository.save(weather).getId();
    }

    @Transactional(readOnly = true)
    public boolean isWeatherEmpty(String day) {
        int cnt = weatherRepository.countByDay(day);
        return cnt < 1;
    }

    @Transactional
    public boolean branchWeather(ArrayList<WeatherSaveDto> weatherSaveDtoList) {
        try {
            for (WeatherSaveDto weatherSaveDto : weatherSaveDtoList) {
                weatherSaveDto = imgURLChange(weatherSaveDto);
                if (isWeatherEmpty(weatherSaveDto.getDay())) {
                    String saveId = saveWeather(weatherSaveDto);
                } else {
                    String updateId = updateWeather(weatherSaveDto);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public WeatherSaveDto imgURLChange(WeatherSaveDto weatherSaveDto) {
        String originURL = weatherSaveDto.getImgURL();
        if(originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s1.gif")) {
            weatherSaveDto.setImgURL("/img/weather/sunny.png");
        } else if(originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s2.gif")
         || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s16.gif")
         || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s21.gif")) {
            weatherSaveDto.setImgURL("/img/weather/cloudSunny.png");
        } else if(originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s3.gif")
        || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s9.gif")
        || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s11.gif")
        || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s22.gif")) {
            weatherSaveDto.setImgURL("/img/weather/cloud.png");
        } else if(originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s4.gif")
                || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s6.gif")
                || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s7.gif")
                || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s8.gif")
                || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s13.gif")
                || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s15.gif")) {
            weatherSaveDto.setImgURL("/img/weather/rain.png");
        } else if(originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s5.gif")
                || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s14.gif")
                || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s19.gif")
                || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s20.gif")) {
            weatherSaveDto.setImgURL("/img/weather/snow.png");
        } else if(originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s10.gif")
                || originURL.equals("https://ssl.pstatic.net/static/weather/images/w_icon/w_s12.gif")) {
            weatherSaveDto.setImgURL("/img/weather/thunder.png");
        } else {
            weatherSaveDto.setImgURL("/img/weather/sunnyRain.png");
        }
        return weatherSaveDto;
    }



}
