package com.TDL.springboot.domain.day;

import com.TDL.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Document(collection = "day")
public class Day {

    @Id
    private String id;

    private String title;

    private String date;

    private String owner;

    @Builder
    public Day(String title, String date, String owner) {
        this.title = title;
        this.date = date;
        this.owner = owner;
    }

    public void update(String title, String date) {
        this.title = title;
        this.date = date;
    }
}
