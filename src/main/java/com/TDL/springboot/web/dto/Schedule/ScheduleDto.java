package com.TDL.springboot.web.dto.Schedule;

import com.TDL.springboot.domain.schedule.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDto {
    private String id;
    private String backgroundColor;
    private String title;
    private String description;
    private String start;
    private String end;
    private String owner;
    private String display;
    private String realEnd;

    @Builder
    public ScheduleDto(
            String id,
            String backgroundColor,
            String title,
            String description,
            String start,
            String end,
            String owner,
            String display,
            String realEnd) {
        this.backgroundColor = backgroundColor;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.owner = owner;
        this.display = display;
        this.realEnd = realEnd;
    }

    public Schedule toDocument() {
        return Schedule.builder()
                .backgroundColor(backgroundColor)
                .title(title)
                .description(description)
                .start(start)
                .end(end)
                .owner(owner)
                .display(display)
                .realEnd(realEnd)
                .build();
    }
}
