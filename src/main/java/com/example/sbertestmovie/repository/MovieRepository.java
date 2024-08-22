package com.example.sbertestmovie.repository;

import com.example.sbertestmovie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностями {@link Movie}.
 * <p>
 * Расширяет {@link JpaRepository} для предоставления стандартных CRUD операций и дополнительных методов для работы с {@link Movie}.
 * </p>
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
