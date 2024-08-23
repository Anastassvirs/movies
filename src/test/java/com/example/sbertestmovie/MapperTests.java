package com.example.sbertestmovie;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.dto.MovieMapper;
import com.example.sbertestmovie.entity.Genre;
import com.example.sbertestmovie.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MapperTests {
    private MovieMapper movieMapper = new MovieMapper();

    private Movie movie;
    private MovieDto movieDto;

    @BeforeEach
    void init() {
        String title = "Wolfwalkers";
        String director = "Tomm Moore";
        LocalDate releaseDate = LocalDate.of(2020, 9, 12);
        Genre genre = Genre.ANIMATION;
        movie = new Movie(title, director, releaseDate, genre);
        movieDto = new MovieDto(title, director, releaseDate, genre);
    }

    @Test
    void testToMovieDto() {
        MovieDto mappedDto = movieMapper.toMovieDto(movie);

        assertThat(mappedDto).isEqualTo(movieDto);
    }
}
