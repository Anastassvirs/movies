package com.example.sbertestmovie;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.entity.Genre;
import com.example.sbertestmovie.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class DtoTests {

    @Autowired
    private JacksonTester<MovieDto> jsonMovieDto;

    private MovieDto movieDto;

    /**
     * Инициализация тестовых данных перед каждым тестом.
     * <p>
     * Этот метод настраивает DTO-объект фильма с заранее определенными значениями.
     * Этот объект будет использоваться в последующих тестовых методах.
     * </p>
     */
    @BeforeEach
    void init() {
        String title = "Wolfwalkers";
        String director = "Tomm Moore";
        LocalDate releaseDate = LocalDate.of(2020, 9, 12);
        Genre genre = Genre.ANIMATION;
        movieDto = new MovieDto(title, director, releaseDate, genre);
    }

    /**
     * Проверка корректности сериализации MovieDto в JSON.
     * @result JSON-вывод будет соответствовать ожидаемым значениям.
     * @throws Exception если сериализация в JSON завершится неудачно
     */
    @Test
    void testMovieDto() throws Exception {
        JsonContent<MovieDto> jsonDto = jsonMovieDto.write(movieDto);

        assertThat(jsonDto).extractingJsonPathStringValue("$.title").isEqualTo("Wolfwalkers");
        assertThat(jsonDto).extractingJsonPathStringValue("$.director").isEqualTo("Tomm Moore");
        assertThat(jsonDto).extractingJsonPathStringValue("$.releaseDate").isEqualTo("2020-09-12");
        assertThat(jsonDto).extractingJsonPathStringValue("$.genre").isEqualTo("ANIMATION");
    }
}

