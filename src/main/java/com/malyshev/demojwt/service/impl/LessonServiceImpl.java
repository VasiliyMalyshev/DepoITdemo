package com.malyshev.demojwt.service.impl;

import com.malyshev.demojwt.dto.LessonDto;
import com.malyshev.demojwt.model.Lesson;
import com.malyshev.demojwt.model.User;
import com.malyshev.demojwt.repository.LessonRepository;
import com.malyshev.demojwt.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void addLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void updateLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }
    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }
    @Override
    public List<LessonDto> findLessonsByUser(User user) {
        List<Lesson> lessonList = getAll();
        List<LessonDto> lessonDtoList = convertEntityToDto(lessonList);

        for (LessonDto lessonDto : lessonDtoList) {
            if (lessonDto.getNumLesson() <= user.getCurrentLesson().getNumLesson()) {
                lessonDto.setIsActive(true);
            } else {
                lessonDto.setIsActive(false);
            }
        }
        return lessonDtoList;
    }

    @Override
    public List<Lesson> getLessonById(Long id) {
        return null;
    }


    public List<LessonDto> findLessonByUser(User user) {
        List<Lesson> lessonList = getAll();
        List<LessonDto> lessonDtoList = convertEntityToDto(lessonList);

        for (LessonDto lessonDto : lessonDtoList) {
            if (lessonDto.getNumLesson() <= user.getCurrentLesson().getNumLesson()) {
                lessonDto.setIsActive(true);
            } else {
                lessonDto.setIsActive(false);
            }
        }
        return lessonDtoList;
    }
    private List<LessonDto> convertEntityToDto(List<Lesson> lesson) {
        return lesson.stream()
                .map(entity -> new LessonDto(entity.getId(), entity.getNumLesson(), entity.getTitle(), entity.getDescription()))
                .collect(Collectors.toList());
    }

}
