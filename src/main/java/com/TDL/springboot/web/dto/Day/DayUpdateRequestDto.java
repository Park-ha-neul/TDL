package com.TDL.springboot.web.dto.Day;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DayUpdateRequestDto {
    private String id;
    private String title;
    private String date;
    private String owner;

    @Builder
    public DayUpdateRequestDto(String id, String title, String date) {
        this.id = id;
        this.title  = title;
        this.date = date;
    }
}
