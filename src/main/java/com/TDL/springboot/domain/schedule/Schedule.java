package com.TDL.springboot.domain.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Document(collection = "schedule")
public class Schedule {
    @Id
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
    public Schedule(
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

    public void update(String title, String description, String backgroundColor) {
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
    }
}
