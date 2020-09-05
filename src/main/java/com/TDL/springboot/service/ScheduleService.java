package com.TDL.springboot.service;

import com.TDL.springboot.config.auth.dto.SessionUser;
import com.TDL.springboot.domain.schedule.Schedule;
import com.TDL.springboot.domain.schedule.ScheduleRepository;
import com.TDL.springboot.web.dto.Schedule.ScheduleDto;
import com.TDL.springboot.web.dto.Schedule.ScheduleUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public String save(ScheduleDto scheduleDto, SessionUser user) {
        scheduleDto.setOwner(user.getEmail());
        return scheduleRepository.save(scheduleDto.toDocument()).getId();
    }
    @Transactional(readOnly = true)
    public List<Schedule> getScheduleList(String email) {
        return scheduleRepository.findAllByOwner(email);
    }

    @Transactional
    public void deleteSchedule(String id) {
      scheduleRepository.deleteById(id);
    }

    @Transactional
    public String updateSchedule(ScheduleUpdateDto scheduleUpdateDto) {
        try {
            Schedule schedule = scheduleRepository.findById(scheduleUpdateDto.getId()).get();
            schedule.update(scheduleUpdateDto.getTitle(), scheduleUpdateDto.getDescription(), scheduleUpdateDto.getBackgroundColor());
            scheduleRepository.save(schedule);
            return schedule.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
