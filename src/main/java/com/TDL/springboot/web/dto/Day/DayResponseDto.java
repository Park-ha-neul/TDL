package com.TDL.springboot.web.dto.Day;

import com.TDL.springboot.domain.day.Day;
import com.TDL.springboot.domain.memo.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DayResponseDto {
    private String id;
    private String title;
    private String date;
    private String owner;

    public DayResponseDto(Day entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.date = entity.getDate();
        this.owner = entity.getOwner();
    }
}
