package com.malyshev.demojwt.service;

import com.malyshev.demojwt.dto.LessonDto;
import com.malyshev.demojwt.model.Lesson;
import com.malyshev.demojwt.model.User;

import java.util.List;

public interface LessonService {
    void addLesson(Lesson lesson);
    void updateLesson(Lesson lesson);
    void delete(Long id);
    List<Lesson> getAll();
    Lesson findById(Long id);
    List<LessonDto> findLessonsByUser(User user);

    List<Lesson> getLessonById(Long id);
}
