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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void findByIdTest() {
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieMapper.toMovieDto(movie)).thenReturn(movieDto1);
        assertEquals(movieDto1, movieService.findById(movieId));
    }

    @Test
    public void saveTest() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieMapper.toMovie(movieDto1)).thenReturn(movie);
        when(movieMapper.toMovieDto(movie)).thenReturn(movieDto1);
        assertEquals(movieDto1, movieService.create(movieDto1));
    }

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

    @Test
    public void updateTest() {
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toMovieDto(movie)).thenReturn(movieDto1);

        assertEquals(movieDto1, movieService.update(movieId, movieDto1));
    }

    @Test
    public void updateErrorsTest() {
        when(movieRepository.findById(movieId)).thenReturn(null);

        Throwable thrown = catchThrowable(() -> {
            movieService.update(movieId, movieDto1);
        });
        assertThat(thrown).isInstanceOf(NotFoundAnythingException.class);
        assertThat(thrown.getMessage()).isNotBlank();
        assertEquals("Фильм с введенным id не найден", thrown.getMessage());
    }

    @Test
    public void deleteTest() {
        when(movieRepository.existsById(movieId)).thenReturn(true);

        movieService.deleteById(movieId);

        verify(movieRepository).deleteById(movieId);
    }
}
