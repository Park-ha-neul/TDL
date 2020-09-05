package com.TDL.springboot.web;

import com.TDL.springboot.config.auth.LoginUser;
import com.TDL.springboot.config.auth.dto.SessionUser;
import com.TDL.springboot.domain.schedule.Schedule;
import com.TDL.springboot.service.ScheduleService;
import com.TDL.springboot.web.dto.Schedule.ScheduleDto;
import com.TDL.springboot.web.dto.Schedule.ScheduleUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/schedule")
    public List<Schedule> getScheduleList(@LoginUser SessionUser user) {
      return scheduleService.getScheduleList(user.getEmail());
    };

    @PostMapping("/schedule")
    public String savePost(@RequestBody ScheduleDto scheduleDto, @LoginUser SessionUser user) {
        return scheduleService.save(scheduleDto, user);
    }

    @DeleteMapping("/schedule/{id}")
    public void deleteSchedule(@PathVariable String id) {
        scheduleService.deleteSchedule(id);
    }

    @PatchMapping("/schedule")
    public String updateSchedule(@RequestBody ScheduleUpdateDto scheduleUpdateDto) {
        return scheduleService.updateSchedule(scheduleUpdateDto);
    }

}
