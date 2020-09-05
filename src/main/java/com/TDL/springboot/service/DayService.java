package com.TDL.springboot.service;

import com.TDL.springboot.config.auth.dto.SessionUser;
import com.TDL.springboot.domain.day.Day;
import com.TDL.springboot.domain.day.DayRepository;
import com.TDL.springboot.domain.user.UserRepository;
import com.TDL.springboot.web.dto.Day.DayResponseDto;
import com.TDL.springboot.web.dto.Day.DaySaveRequestDto;
import com.TDL.springboot.web.dto.Day.DayUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DayService {
    private final DayRepository dayRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Day> getDayListTen(SessionUser sessionUser) {
        return dayRepository.findAllByOwner(sessionUser.getEmail());
    }

    @Transactional
    public String save(DaySaveRequestDto daySaveRequestDto, SessionUser sessionUser) {
        daySaveRequestDto.setOwner(sessionUser.getEmail());
        return dayRepository.save(daySaveRequestDto.toDocument()).getId();
    }

    @Transactional(readOnly = true)
    public DayResponseDto dayFindById(String id) {
        Day entity = dayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 디데이가 업는데용 ㅋㅋ id=" + id));

        return new DayResponseDto(entity);
    }

    @Transactional
    public String update(DayUpdateRequestDto requestDto) {
        Day day = dayRepository.findById(requestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + requestDto.getId()));
        day.update(requestDto.getTitle(), requestDto.getDate());
        dayRepository.save(day);

        return day.getId();
    }

    //삭제
    @Transactional
    public String delete(String id) {
        Day day = dayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" +id));
        dayRepository.delete(day);

        return day.getId();
    }

    @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}
