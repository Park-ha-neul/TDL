package com.TDL.springboot.web;

import com.TDL.springboot.config.auth.LoginUser;
import com.TDL.springboot.config.auth.dto.SessionUser;
import com.TDL.springboot.domain.day.Day;
import com.TDL.springboot.service.DayService;
import com.TDL.springboot.web.dto.Day.DayResponseDto;
import com.TDL.springboot.web.dto.Day.DaySaveRequestDto;
import com.TDL.springboot.web.dto.Day.DayUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DayApiController {
    private final DayService dayService;

    // 날짜 생성
    @GetMapping("/api/v1/day")
    public List<Day> getDayListTen(@LoginUser SessionUser sessionUser) {
        return dayService.getDayListTen(sessionUser);
    }

    // 날짜 생성
    @GetMapping("/api/v1/day/{id}")
    public DayResponseDto dayFindById(@PathVariable String id) {
        return dayService.dayFindById(id);
    }

    //수정
    @PatchMapping("/api/v1/day")
    public String update(@RequestBody DayUpdateRequestDto requestDto) {
        return dayService.update(requestDto);
    }


    // 날짜 조회
    @PostMapping("/api/v1/day")
    public String save(@RequestBody DaySaveRequestDto daySaveRequestDto, @LoginUser SessionUser sessionUser) {
        return dayService.save(daySaveRequestDto, sessionUser);
    }

    //삭제
    @DeleteMapping("/api/v1/day/{id}")
    public String delete(@PathVariable String id) {
        return dayService.delete(id);
    }

    @DeleteMapping("/api/v1/user")
    public void delete(@LoginUser SessionUser sessionUser) {
        dayService.deleteByEmail(sessionUser.getEmail());
    }
}
