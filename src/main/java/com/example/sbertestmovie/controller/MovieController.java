package com.example.sbertestmovie.controller;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления фильмами.
 * <p>
 * Контроллер обрабатывает HTTP-запросы для операций с фильмами:
 *     получение списка фильмов
 *     создание фильмов
 *     обновление фильмов
 *     удаление фильмов.
 * Методы контроллера делегируют выполнение соответствующих операций в сервисный слой.
 * </p>
 */
@RestController
@RequestMapping(path = "/movies")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    /**
     * Получение списка фильмов с поддержкой пагинации.
     * <p>
     * Возвращает страницу с фильмами на основе предоставленного параметра
     * пагинации.
     * </p>
     *
     * @param pageable параметры пагинации (номер страницы, размер страницы и т.д.)
     * @return страница объектов {@link MovieDto}, представляющих фильмы
     */
    @GetMapping
    public Page<MovieDto> findAll(@PageableDefault Pageable pageable) {
        return movieService.getAllPageable(pageable);
    }

    /**
     * Получение фильма по его идентификатору.
     * <p>
     * Возвращает объект {@link MovieDto} для фильма с указанным идентификатором.
     * </p>
     *
     * @param movieId идентификатор фильма, который нужно получить
     * @return объект {@link MovieDto} с данными фильма
     */
    public MovieDto find(@PathVariable Long movieId) {
        return movieService.findById(movieId);
    }

    /**
     * Создание нового фильма.
     * <p>
     * Создаёт новый фильм на основе данных, предоставленных в теле запроса в виде dto-объекта.
     * </p>
     *
     * @param movieDto объект {@link MovieDto}, содержащий данные для нового фильма
     * @return объект {@link MovieDto} с данными созданного фильма
     */
    @PostMapping
    public MovieDto create(@RequestBody MovieDto movieDto) {
        return movieService.create(movieDto);
    }

    /**
     * Обновление существующего фильма.
     * <p>
     * Обновляет фильм с указанным идентификатором на основе данных, предоставленных
     * в теле запроса в виде dto-объекта.
     * </p>
     *
     * @param movieId идентификатор фильма, который нужно обновить
     * @param movieDto объект {@link MovieDto}, содержащий обновлённые данные фильма
     * @return объект {@link MovieDto} с обновлёнными данными фильма
     */
    @PatchMapping(path = "/{movieId}")
    public MovieDto update(@PathVariable Long movieId, @RequestBody MovieDto movieDto) {
        return movieService.update(movieId, movieDto);
    }

    /**
     * Удаление фильма по его идентификатору.
     * <p>
     * Удаляет фильм с указанным идентификатором из базы данных.
     * </p>
     *
     * @param movieId идентификатор фильма, который нужно удалить
     */
    @DeleteMapping(path = "/{movieId}")
    public void deleteById(@PathVariable Long movieId) {
        movieService.deleteById(movieId);
    }
}
