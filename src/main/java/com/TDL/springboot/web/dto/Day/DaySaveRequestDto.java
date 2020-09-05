package com.TDL.springboot.web.dto.Day;

import com.TDL.springboot.domain.day.Day;
import com.TDL.springboot.domain.schedule.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DaySaveRequestDto {
    String title;
    String date;
    String owner;

    @Builder
    public DaySaveRequestDto(
            String title,
            String date,
            String owner) {
        this.title = title;
        this.date = date;
        this.owner = owner;
    }

    public Day toDocument() {
        return Day.builder()
                .title(title)
                .date(date)
                .owner(owner)
                .build();
    }
}
