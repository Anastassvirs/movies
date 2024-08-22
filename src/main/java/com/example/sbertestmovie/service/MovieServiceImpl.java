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

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public Page<MovieDto> getAllPageable(Pageable pageable) {
        return movieMapper.toPageMovieDto(movieRepository.findAll(pageable));
    }

    @Override
    public MovieDto findById(Long id) {
        return movieMapper.toMovieDto(getById(id));
    }

    @Override
    public Movie getById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new NotFoundAnythingException("Фильма с данным id не существует"));
    }

    @Transactional
    @Override
    public MovieDto create(MovieDto movieDto) {
        validateMovieDto(movieDto);
        Movie movie = movieMapper.toMovie(movieDto);
        log.debug("СОБЫТИЕ: Добавляем новый фильм: {}", movieDto);
        try {
            return movieMapper.toMovieDto(movieRepository.save(movie));
        } catch (Exception e) {
            log.debug("ОШИБКА: Не удалось создать фильм");
            throw new SaveException("Не удалось создать фильм");
        }
    }

    private void validateMovieDto(MovieDto movieDto) {
        if (Objects.isNull(movieDto.getTitle()) || Objects.isNull(movieDto.getDirector()) ||
                Objects.isNull(movieDto.getReleaseDate()) || Objects.isNull(movieDto.getGenre()) ||
                movieDto.getTitle().isEmpty() || movieDto.getDirector().isEmpty()) {
            throw new WrongParametersException("Неправильно заполнены поля создаваемого фильма");
        }
    }

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
