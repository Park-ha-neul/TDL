package com.TDL.springboot.web.dto.Day;

import com.TDL.springboot.domain.day.Day;
import com.TDL.springboot.domain.memo.Memo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DayListResponseDto {
    private String title;
    private String date;
    private String owner;

    public DayListResponseDto(Day entity) {
        this.title = entity.getTitle();
        this.date = entity.getDate();
        this.owner = entity.getOwner();
    }
}
