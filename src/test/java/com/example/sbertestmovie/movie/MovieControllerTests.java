package com.example.sbertestmovie.movie;

import com.example.sbertestmovie.controller.MovieController;
import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.entity.Genre;
import com.example.sbertestmovie.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    private MovieDto movieDto1;
    private MovieDto movieDto2;
    private List<MovieDto> movieDtos;
    private Page<MovieDto> movieDtosPage;

    @BeforeEach
    void setUp() {
        movieDto1 = new MovieDto("Wolfwalkers", "Tomm Moore", LocalDate.of(2020, 9, 12), Genre.ANIMATION);
        movieDto2 = new MovieDto("Song of the Sea", "Tomm Moore", LocalDate.of(2014, 11, 6), Genre.ANIMATION);

        movieDtos = Arrays.asList(movieDto1, movieDto2);
        movieDtosPage = new PageImpl<>(movieDtos, PageRequest.of(0, 10), movieDtos.size());
    }

    /**
     * Тест получения всех фильмов с поддержкой пагинации.
     * @result Возвращается страница объектов {@link MovieDto}, соответствующая данным, полученным из сервиса.
     */
    @Test
    public void findAllTest() throws Exception {
        when(movieService.getAllPageable(any(Pageable.class))).thenReturn(movieDtosPage);

        mockMvc.perform(get("/movies")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "title,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(movieDtos.size()))
                .andExpect(jsonPath("$.content[0].title").value("Wolfwalkers"))
                .andExpect(jsonPath("$.content[1].title").value("Song of the Sea"));
    }

    /**
     * Тест поиска фильма по идентификатору.
     * @result Возвращается объект {@link MovieDto}, соответствующий найденной сущности фильма.
     */
    @Test
    public void findByIdTest() throws Exception {
        when(movieService.findById(anyLong())).thenReturn(movieDto1);

        mockMvc.perform(get("/movies/{movieId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Wolfwalkers"))
                .andExpect(jsonPath("$.director").value("Tomm Moore"))
                .andExpect(jsonPath("$.releaseDate").value("2020-09-12"))
                .andExpect(jsonPath("$.genre").value("ANIMATION"));
    }

    /**
     * Тест создания нового фильма.
     * @result Новый фильм сохраняется через сервис, и возвращается объект {@link MovieDto} с данными созданного фильма.
     */
    @Test
    public void createTest() throws Exception {
        when(movieService.create(any(MovieDto.class))).thenReturn(movieDto1);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Wolfwalkers\",\"director\":\"Tomm Moore\",\"releaseDate\":\"2020-09-12\",\"genre\":\"ANIMATION\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Wolfwalkers"))
                .andExpect(jsonPath("$.director").value("Tomm Moore"))
                .andExpect(jsonPath("$.releaseDate").value("2020-09-12"))
                .andExpect(jsonPath("$.genre").value("ANIMATION"));
    }

    /**
     * Тест обновления существующего фильма.
     * @result Фильм с указанным идентификатором обновляется через сервис, и возвращается обновленный объект {@link MovieDto}.
     */
    @Test
    public void updateTest() throws Exception {
        when(movieService.update(anyLong(), any(MovieDto.class))).thenReturn(movieDto1);

        mockMvc.perform(patch("/movies/{movieId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Wolfwalkers\",\"director\":\"Tomm Moore\",\"releaseDate\":\"2020-09-12\",\"genre\":\"ANIMATION\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Wolfwalkers"))
                .andExpect(jsonPath("$.director").value("Tomm Moore"))
                .andExpect(jsonPath("$.releaseDate").value("2020-09-12"))
                .andExpect(jsonPath("$.genre").value("ANIMATION"));
    }

    /**
     * Тест удаления фильма по идентификатору.
     * @result Фильм с указанным идентификатором удаляется через сервис, и возвращается успешный статус.
     */
    @Test
    public void deleteTest() throws Exception {
        doNothing().when(movieService).deleteById(anyLong());

        mockMvc.perform(delete("/movies/{movieId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(movieService).deleteById(1L);
    }
}