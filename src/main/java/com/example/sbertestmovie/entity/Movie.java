package com.example.sbertestmovie.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Сущность, представляющая фильм.
 * <p>
 * Этот класс отображает объект фильма в базе данных и содержит все
 * необходимые поля, такие как идентификатор, заголовок, режиссёр,
 * дата релиза и жанр.
 * </p>
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "movies")
public class Movie {

    /**
     * Уникальный идентификатор фильма.
     * <p>
     * Значение автоматически генерируется базой данных при создании
     * нового фильма.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * Название фильма.
     * <p>
     * Должно быть задано и не может быть пустым.
     * </p>
     */
    @Column(nullable = false)
    private String title;

    /**
     * Режиссёр фильма.
     * <p>
     * Должен быть задан и не может быть пустым.
     * </p>
     */
    @Column(nullable = false)
    private String director;

    /**
     * Дата релиза фильма.
     * <p>
     * Должна быть задана и не может быть пустой.
     * </p>
     */
    @Column(nullable = false)
    private LocalDate releaseDate;

    /**
     * Жанр фильма.
     * <p>
     * Должен быть задан и не может быть пустым.
     * Реализован как Enum-объект и содержит значения
     * на выбор:
     *     ACTION,
     *     COMEDY,
     *     DRAMA,
     *     ROMANCE,
     *     FANTASY,
     *     ANIMATION
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    /**
     * Конструктор для создания нового экземпляра фильма с указанными
     * заголовком, режиссёром, датой релиза и жанром.
     *
     * @param title заголовок фильма
     * @param director режиссёр фильма
     * @param releaseDate дата релиза фильма
     * @param genre жанр фильма
     */
    public Movie(String title, String director, LocalDate releaseDate, Genre genre) {
        this.title = title;
        this.director = director;
        this.releaseDate = releaseDate;
        this.genre = genre;
    }
}
