package com.example.sbertestmovie.movie;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.dto.MovieMapper;
import com.example.sbertestmovie.entity.Genre;
import com.example.sbertestmovie.entity.Movie;
import com.example.sbertestmovie.exception.NotFoundAnythingException;
import com.example.sbertestmovie.exception.WrongParametersException;
import com.example.sbertestmovie.repository.MovieRepository;
import com.example.sbertestmovie.service.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTests {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    private Long movieId;
    private Movie movie;
    private Movie movie2;
    private MovieDto movieDto1;
    private MovieDto movieDto2;
    private List<Movie> movies;
    private List<MovieDto> movieDtos;
    private Page<Movie> moviesPage;
    private Page<MovieDto> movieDtosPage;

    @BeforeEach
    void init() {
        movieId = 1L;
        movie = new Movie("Wolfwalkers", "Tomm Moore", LocalDate.of(2020, 9, 12), Genre.ANIMATION);
        movie.setId(movieId);
        movie2 = new Movie("Song of the Sea", "Tomm Moore", LocalDate.of(2014, 11, 6), Genre.ANIMATION);
        movieDto1 = new MovieDto("Wolfwalkers", "Tomm Moore", LocalDate.of(2020, 9, 12), Genre.ANIMATION);
        movieDto2 = new MovieDto("Song of the Sea", "Tomm Moore", LocalDate.of(2014, 11, 6), Genre.ANIMATION);
        movies = Arrays.asList(movie, movie2);
        movieDtos = Arrays.asList(movieDto1, movieDto2);
        moviesPage = new PageImpl<>(movies, PageRequest.of(0, 10, Sort.by("title")), movieDtos.size());
        movieDtosPage = new PageImpl<>(movieDtos, PageRequest.of(0, 10, Sort.by("title")), movieDtos.size());
    }

    /**
     * Тест получения всех фильмов с поддержкой пагинации.
     * @result Возвращается страница объектов {@link MovieDto}, соответствующая данным, полученным из репозитория.
     */
    @Test
    public void findAllTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));

        when(movieRepository.findAll(pageable)).thenReturn(moviesPage);
        when(movieMapper.toPageMovieDto(moviesPage)).thenReturn(movieDtosPage);

        Page<MovieDto> result = movieService.getAllPageable(pageable);

        assertEquals(movieDtosPage, result);
        assertEquals(movieDtos.size(), result.getContent().size());
        assertEquals("Wolfwalkers", result.getContent().get(0).getTitle());
        assertEquals("Song of the Sea", result.getContent().get(1).getTitle());
        assertEquals(0, result.getNumber());
        assertEquals(10, result.getSize());
        assertEquals(1, result.getTotalPages());
        assertEquals(movieDtos.size(), result.getTotalElements());
        assertEquals(movieDtos.size(), result.getNumberOfElements());
    }

    /**
     * Тест поиска фильма по идентификатору.
     * @result Возвращается объект {@link MovieDto}, соответствующий найденной сущности фильма.
     */
    @Test
    public void findByIdTest() {
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieMapper.toMovieDto(movie)).thenReturn(movieDto1);
        assertEquals(movieDto1, movieService.findById(movieId));
    }

    /**
     * Тест создания нового фильма.
     * @result Новый фильм сохраняется в базе данных, и возвращается объект {@link MovieDto} с данными созданного фильма.
     */
    @Test
    public void saveTest() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieMapper.toMovie(movieDto1)).thenReturn(movie);
        when(movieMapper.toMovieDto(movie)).thenReturn(movieDto1);
        assertEquals(movieDto1, movieService.create(movieDto1));
    }

    /**
     * Тест создания фильма с недопустимыми значениями полей (null).
     * @result Генерируется исключение {@link WrongParametersException}, указывающее на некорректность данных.
     */
    @Test
    public void saveNullFieldsTest() {
        MovieDto nullFieldDto1 = new MovieDto(null, "Tomm Moore", LocalDate.of(2020, 9, 12), Genre.ANIMATION);
        Throwable thrown = catchThrowable(() -> {
            movieService.create(nullFieldDto1);
        });
        assertThat(thrown).isInstanceOf(WrongParametersException.class);
        assertThat(thrown.getMessage()).isNotBlank();
        assertEquals("Неправильно заполнены поля создаваемого фильма", thrown.getMessage());

        MovieDto nullFieldDto2 = new MovieDto("Wolfwalkers", null, LocalDate.of(2020, 9, 12), Genre.ANIMATION);
        thrown = catchThrowable(() -> {
            movieService.create(nullFieldDto2);
        });
        assertThat(thrown).isInstanceOf(WrongParametersException.class);
        assertThat(thrown.getMessage()).isNotBlank();
        assertEquals("Неправильно заполнены поля создаваемого фильма", thrown.getMessage());

        MovieDto nullFieldDto3 = new MovieDto("Wolfwalkers", "Tomm Moore", null, Genre.ANIMATION);
        thrown = catchThrowable(() -> {
            movieService.create(nullFieldDto3);
        });
        assertThat(thrown).isInstanceOf(WrongParametersException.class);
        assertThat(thrown.getMessage()).isNotBlank();
        assertEquals("Неправильно заполнены поля создаваемого фильма", thrown.getMessage());

        MovieDto nullFieldDto4 = new MovieDto("Wolfwalkers", "Tomm Moore", LocalDate.of(2020, 9, 12), null);
        thrown = catchThrowable(() -> {
            movieService.create(nullFieldDto4);
        });
        assertThat(thrown).isInstanceOf(WrongParametersException.class);
        assertThat(thrown.getMessage()).isNotBlank();
        assertEquals("Неправильно заполнены поля создаваемого фильма", thrown.getMessage());
    }

    /**
     * Тест обновления существующего фильма.
     * @result Фильм с указанным идентификатором обновляется и возвращается обновленный объект {@link MovieDto}.
     */
    @Test
    public void updateTest() {
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toMovieDto(movie)).thenReturn(movieDto1);

        assertEquals(movieDto1, movieService.update(movieId, movieDto1));
    }

    /**
     * Тест обновления фильма с ошибками (несуществующий фильм).
     * @result Генерируется исключение {@link NotFoundAnythingException}, указывающее, что фильм не найден.
     */
    @Test
    public void updateErrorsTest() {
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> {
            movieService.update(movieId, movieDto1);
        });
        assertThat(thrown).isInstanceOf(NotFoundAnythingException.class);
        assertThat(thrown.getMessage()).isNotBlank();
        assertEquals("Фильм с введенным id не найден", thrown.getMessage());
    }

    /**
     * Тест удаления фильма по идентификатору.
     * @result Фильм с указанным идентификатором удаляется из базы данных.
     */
    @Test
    public void deleteTest() {
        when(movieRepository.existsById(movieId)).thenReturn(true);

        movieService.deleteById(movieId);

        verify(movieRepository).deleteById(movieId);

        verifyNoMoreInteractions(movieRepository);
    }
}
