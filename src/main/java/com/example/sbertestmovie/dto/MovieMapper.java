package com.example.sbertestmovie.dto;

import com.example.sbertestmovie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public MovieDto toMovieDto(Movie movie) {
        return new MovieDto(
                movie.getTitle(),
                movie.getDirector(),
                movie.getReleaseDate(),
                movie.getGenre()
        );
    }

    public Movie toMovie(MovieDto movieDto) {
        return new Movie(
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getReleaseDate(),
                movieDto.getGenre()
        );
    }

    public Page<MovieDto> toPageMovieDto(Page<Movie> moviesPage) {
        return moviesPage.map(this::toMovieDto);
    }
}
