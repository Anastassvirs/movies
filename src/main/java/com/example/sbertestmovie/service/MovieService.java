package com.example.sbertestmovie.service;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MovieService {

    Page<MovieDto> getAllPageable(Pageable pageable);

    MovieDto findById(Long id);

    Movie getById(Long id);

    MovieDto create(MovieDto movieDto);

    MovieDto update(Long id, MovieDto movieDto);

    void deleteById(Long id);
}
