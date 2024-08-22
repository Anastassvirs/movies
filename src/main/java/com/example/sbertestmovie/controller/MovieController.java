package com.example.sbertestmovie.controller;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/movies")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public Page<MovieDto> findAll(@PageableDefault Pageable pageable) {
        return movieService.getAllPageable(pageable);
    }

    public MovieDto find(@PathVariable Long movieId) {
        return movieService.findById(movieId);
    }

    @PostMapping
    public MovieDto create(@RequestBody MovieDto movieDto) {
        return movieService.create(movieDto);
    }

    @PatchMapping(path = "/{movieId}")
    public MovieDto update(@PathVariable Long movieId, @RequestBody MovieDto movieDto) {
        return movieService.update(movieId, movieDto);
    }

    @DeleteMapping(path = "/{movieId}")
    public void deleteById(@PathVariable Long movieId) {
        movieService.deleteById(movieId);
    }
}
