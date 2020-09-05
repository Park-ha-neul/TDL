package com.TDL.springboot.domain.weather;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherRepository extends MongoRepository<Weather, String> {
    int countByDay(String Day);

    Weather findByDay(String day);
}
