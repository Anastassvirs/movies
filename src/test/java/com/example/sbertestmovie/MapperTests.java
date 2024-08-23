package com.example.sbertestmovie;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.dto.MovieMapper;
import com.example.sbertestmovie.entity.Genre;
import com.example.sbertestmovie.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MapperTests {
    private MovieMapper movieMapper = new MovieMapper();

    private Movie movie;
    private MovieDto movieDto;
    private List<Movie> movieList;
    private List<MovieDto> movieDtoList;
    private Page<Movie> moviePage;
    private Page<MovieDto> movieDtoPage;

    /**
     * Инициализация тестовых данных перед каждым тестом.
     * <p>
     * Этот метод настраивает объект фильма и соответствующий ему DTO с заранее определенными значениями.
     * Эти объекты будут использоваться для тестирования преобразований в методах маппера.
     * </p>
     */
    @BeforeEach
    void init() {
        String title = "Wolfwalkers";
        String director = "Tomm Moore";
        LocalDate releaseDate = LocalDate.of(2020, 9, 12);
        Genre genre = Genre.ANIMATION;
        movie = new Movie(title, director, releaseDate, genre);
        movieDto = new MovieDto(title, director, releaseDate, genre);

        movieList = Arrays.asList(movie);
        movieDtoList = Arrays.asList(movieDto);
        moviePage = new PageImpl<>(movieList, PageRequest.of(0, 10), movieList.size());
        movieDtoPage = new PageImpl<>(movieDtoList, PageRequest.of(0, 10), movieDtoList.size());

    }

    /**
     * Проверка преобразования объекта Movie в MovieDto.
     * <p>
     * Этот тест проверяет, что метод {@link MovieMapper#toMovieDto(Movie)} корректно преобразует объект
     * {@link Movie} в объект {@link MovieDto}.
     * </p>
     * @result Возвращаемый объект должен быть равен заранее определенному
     *         объекту {@link MovieDto} с соответствующими оригиналу полями.
     */
    @Test
    void testToMovieDto() {
        MovieDto mappedDto = movieMapper.toMovieDto(movie);

        assertThat(mappedDto).isEqualTo(movieDto);
    }

    /**
     * Тест обратного преобразования из DTO {@link MovieDto} в сущность {@link Movie}.
     * @result Возвращается корректный объект {@link Movie}, который полностью совпадает с ожидаемым.
     */
    @Test
    void testToMovie() {
        Movie mappedMovie = movieMapper.toMovie(movieDto);

        assertThat(mappedMovie).isEqualTo(movie);
    }

    /**
     * Тест преобразования страницы сущностей {@link Movie} в страницу {@link MovieDto}.
     * @result Возвращается корректная страница объектов {@link MovieDto}, полностью соответствующая странице сущностей {@link Movie}.
     */
    @Test
    void testToPageMovieDto() {
        Page<MovieDto> mappedPageDto = movieMapper.toPageMovieDto(moviePage);

        assertThat(mappedPageDto).isEqualTo(movieDtoPage);
    }
}
