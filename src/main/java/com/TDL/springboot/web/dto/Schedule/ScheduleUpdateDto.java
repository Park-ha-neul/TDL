package com.TDL.springboot.web.dto.Schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleUpdateDto {
    private String id;
    private String backgroundColor;
    private String title;
    private String description;
}
