package com.TDL.springboot.domain.weather;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Document(collection = "weather")
public class Weather {
    @Id
    private String id;

    @Indexed
    private String day;
    
    private String imgURL;

    @Builder
    public Weather(
            String day,
            String imgURL) {
        this.day = day;
        this.imgURL = imgURL;
    }

    public void update(String imgURL) {
        this.imgURL = imgURL;
    }
}
