package com.malyshev.demojwt.repository;

import com.malyshev.demojwt.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "select MAX(num_lesson) from lessons", nativeQuery = true)
    Long dbSize();

    Lesson findByNumLesson(Long numLesson);
}
