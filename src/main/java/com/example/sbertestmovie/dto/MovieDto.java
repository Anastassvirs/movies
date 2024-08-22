package com.example.sbertestmovie.dto;

import com.example.sbertestmovie.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class MovieDto {
    private String title;
    private String director;
    private LocalDate releaseDate;
    private Genre genre;
}
