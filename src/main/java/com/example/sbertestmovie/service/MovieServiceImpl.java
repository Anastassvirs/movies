package com.example.sbertestmovie.service;

import com.example.sbertestmovie.dto.MovieDto;
import com.example.sbertestmovie.dto.MovieMapper;
import com.example.sbertestmovie.entity.Movie;
import com.example.sbertestmovie.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public MovieDto findById(Long id) {
        return null;
    }

    @Override
    public Movie getById(Long id) {
        return null;
    }

    @Override
    public MovieDto create(MovieDto movieDto) {
        return null;
    }

    @Override
    public MovieDto update(Long id, MovieDto movieDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
