package com.example.sbertestmovie.dto;

import com.example.sbertestmovie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования между сущностями {@link Movie} и {@link MovieDto}.
 * <p>
 * Этот компонент содержит методы для преобразования данных между уровнями
 * сущностей и DTO. Это необходимо для передачи данных между слоями приложения.
 * </p>
 */
@Component
public class MovieMapper {

    /**
     * Преобразует сущность {@link Movie} в {@link MovieDto}.
     *
     * @param movie сущность фильма, которую нужно преобразовать
     * @return объект {@link MovieDto}, представляющий фильм
     */
    public MovieDto toMovieDto(Movie movie) {
        return new MovieDto(
                movie.getTitle(),
                movie.getDirector(),
                movie.getReleaseDate(),
                movie.getGenre()
        );
    }

    /**
     * Преобразует {@link MovieDto} в сущность {@link Movie}.
     *
     * @param movieDto объект DTO фильма, который нужно преобразовать
     * @return сущность {@link Movie}, представляющая фильм
     */
    public Movie toMovie(MovieDto movieDto) {
        return new Movie(
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getReleaseDate(),
                movieDto.getGenre()
        );
    }

    /**
     * Преобразует страницу сущностей {@link Movie} в страницу {@link MovieDto}.
     *
     * @param moviesPage страница сущностей фильма
     * @return страница объектов {@link MovieDto}, представляющая фильмы
     */
    public Page<MovieDto> toPageMovieDto(Page<Movie> moviesPage) {
        return moviesPage.map(this::toMovieDto);
    }
}
