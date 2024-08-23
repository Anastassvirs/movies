package com.example.sbertestmovie.service;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.dto.MovieMapper;
import com.example.sbertestmovie.entity.Movie;
import com.example.sbertestmovie.exception.NotFoundAnythingException;
import com.example.sbertestmovie.exception.SaveException;
import com.example.sbertestmovie.exception.WrongParametersException;
import com.example.sbertestmovie.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Реализация сервиса для работы с фильмами.
 * Предоставляет методы для создания, обновления, удаления и получения фильмов.
 */
@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    /**
     * Конструктор для инициализации MovieServiceImpl.
     *
     * @param movieRepository репозиторий фильмов
     * @param movieMapper маппер для преобразования entity-объектов в dto-объекты и обратно
     */
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    /**
     * Получение списка всех фильмов с поддержкой пагинации.
     *
     * @param pageable объект Pageable для настройки пагинации
     * @return страница с DTO-объектами фильмов
     */
    @Override
    public Page<MovieDto> getAllPageable(Pageable pageable) {
        log.debug("СОБЫТИЕ: Получение списка фильмов");
        return movieMapper.toPageMovieDto(movieRepository.findAll(pageable));
    }

    /**
     * Поиск фильма по идентификатору.
     *
     * @param id идентификатор фильма
     * @return DTO-объект фильма
     * @throws NotFoundAnythingException если фильм с переданным id не найден
     */
    @Override
    public MovieDto findById(Long id) {
        log.debug("СОБЫТИЕ: Получение dto-объекта фильма по id: {}", id);
        return movieMapper.toMovieDto(getById(id));
    }

    /**
     * Получение фильма по идентификатору.
     *
     * @param id идентификатор фильма
     * @return объект фильма
     * @throws NotFoundAnythingException если фильм с переданным id не найден
     */
    @Override
    public Movie getById(Long id) {
        log.debug("СОБЫТИЕ: Получение фильма по id: {}", id);
        return movieRepository.findById(id).orElseThrow(() -> new NotFoundAnythingException("Фильм с введенным id не найден"));
    }

    /**
     * Создание нового фильма.
     *
     * @param movieDto DTO-объект фильма для создания
     * @return DTO-объект созданного фильма
     * @throws WrongParametersException если параметры фильма некорректны
     * @throws SaveException если произошла ошибка при сохранении фильма
     */
    @Transactional
    @Override
    public MovieDto create(MovieDto movieDto) {
        validateMovieDto(movieDto);
        Movie movie = movieMapper.toMovie(movieDto);
        log.debug("СОБЫТИЕ: Добавляем новый фильм: {}", movieDto);
        try {
            movie = movieRepository.save(movie);
            return movieMapper.toMovieDto(movie);
        } catch (Exception e) {
            log.debug("ОШИБКА: Не удалось создать фильм");
            throw new SaveException("Не удалось создать фильм");
        }
    }

    /**
     * Проверка валидности полей DTO-объекта фильма. Вспомогательный метод.
     *
     * @param movieDto DTO-объект фильма для проверки
     * @throws WrongParametersException если одно или несколько полей не заполнены
     */
    private void validateMovieDto(MovieDto movieDto) {
        if (Objects.isNull(movieDto.getTitle()) || Objects.isNull(movieDto.getDirector()) ||
                Objects.isNull(movieDto.getReleaseDate()) || Objects.isNull(movieDto.getGenre()) ||
                movieDto.getTitle().isEmpty() || movieDto.getDirector().isEmpty()) {
            log.debug("ОШИБКА: Одно или часть полей не заполнены");
            throw new WrongParametersException("Неправильно заполнены поля создаваемого фильма");
        }
    }

    /**
     * Обновление информации о фильме.
     *
     * @param id идентификатор фильма
     * @param movieDto передаваемый DTO-объект с обновлённой информацией
     * @return DTO-объект обновлённого фильма
     * @throws NotFoundAnythingException если фильм с переданным id не найден
     */
    @Transactional
    @Override
    public MovieDto update(Long id, MovieDto movieDto) {
        Movie movie;
        try {
            movie = getById(id);
        } catch (Exception e) {
            log.debug("ОШИБКА: Фильм с введенным id не найден");
            throw new NotFoundAnythingException("Фильм с введенным id не найден");
        }

        Optional.ofNullable(movieDto.getTitle()).ifPresent(movie::setTitle);
        Optional.ofNullable(movieDto.getDirector()).ifPresent(movie::setDirector);
        Optional.ofNullable(movieDto.getReleaseDate()).ifPresent(movie::setReleaseDate);
        Optional.ofNullable(movieDto.getGenre()).ifPresent(movie::setGenre);

        log.debug("Обновлен фильм: {}", movie);
        return movieMapper.toMovieDto(movieRepository.save(movie));
    }

    /**
     * Удаление фильма по идентификатору.
     *
     * @param id идентификатор фильма
     * @throws NotFoundAnythingException если фильм с переданным id не найден
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        if (movieRepository.existsById(id)) {
            log.debug("Удалён фильм с id: {}", id);
            movieRepository.deleteById(id);
        } else {
            log.debug("ОШИБКА: Фильм с введенным id не найден");
            throw new NotFoundAnythingException("Фильм с введенным id не найден");
        }
    }
}
