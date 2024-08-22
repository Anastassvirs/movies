package com.example.sbertestmovie.service;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Интерфейс сервиса для работы с фильмами.
 * Определяет методы для выполнения CRUD операций над объектами фильмов.
 */
public interface MovieService {

    /**
     * Получение страницы фильмов с поддержкой пагинации.
     *
     * @param pageable объект Pageable для настройки пагинации
     * @return страница с DTO-объектами фильмов
     */
    Page<MovieDto> getAllPageable(Pageable pageable);

    /**
     * Поиск фильма по идентификатору.
     *
     * @param id идентификатор фильма
     * @return DTO-объект фильма
     */
    MovieDto findById(Long id);

    /**
     * Получение фильма по идентификатору.
     *
     * @param id идентификатор фильма
     * @return объект фильма
     */
    Movie getById(Long id);

    /**
     * Создание нового фильма.
     *
     * @param movieDto DTO-объект фильма для создания
     * @return DTO-объект созданного фильма
     */
    MovieDto create(MovieDto movieDto);

    /**
     * Обновление информации о фильме.
     *
     * @param id идентификатор фильма
     * @param movieDto DTO-объект с обновлённой информацией
     * @return DTO-объект обновлённого фильма
     */
    MovieDto update(Long id, MovieDto movieDto);

    /**
     * Удаление фильма по идентификатору.
     *
     * @param id идентификатор фильма
     */
    void deleteById(Long id);
}
