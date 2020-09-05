package com.TDL.springboot.domain.day;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DayRepository extends MongoRepository<Day, String> {

    List<Day> findAllByOwner(String email);
}
