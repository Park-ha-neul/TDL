package com.TDL.springboot.domain.schedule;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    List<Schedule> findAllByOwner(String Owner);
}
