package com.example.sbertestmovie.dto;

import com.example.sbertestmovie.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Объект передачи данных (DTO), представляющий фильм.
 * <p>
 * Этот класс используется для передачи данных о фильме между различными
 * слоями приложения. Содержит все необходимые поля для описания фильма.
 * </p>
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class MovieDto {

    /**
     * Заголовок фильма.
     * <p>
     * Должен содержать название фильма.
     * </p>
     */
    private String title;

    /**
     * Режиссёр фильма.
     * <p>
     * Должен содержать имя режиссёра фильма.
     * </p>
     */
    private String director;

    /**
     * Дата релиза фильма.
     * <p>
     * Должна содержать дату, когда фильм был выпущен.
     * </p>
     */
    private LocalDate releaseDate;

    /**
     * Жанр фильма.
     * <p>
     * Должен содержать жанр фильма, который определяется перечислением {@link Genre}.
     * </p>
     */
    private Genre genre;
}
