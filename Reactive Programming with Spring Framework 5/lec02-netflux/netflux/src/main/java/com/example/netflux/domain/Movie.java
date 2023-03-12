package com.example.netflux.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor @NoArgsConstructor
public class Movie {
//    @Id
    private String id;

    @NonNull
    private String title;

    public Movie(@NonNull String title) {
        this.title = title;
    }
}
