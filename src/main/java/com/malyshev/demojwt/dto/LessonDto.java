package com.malyshev.demojwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.malyshev.demojwt.model.Lesson;
import com.malyshev.demojwt.model.Status;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonDto {

    private Long id;
    private Long numLesson;
    private String title;
    private String description;

    private Boolean isActive;

    public LessonDto(Lesson lesson) {
        this.id = lesson.getId();
        this.numLesson = lesson.getNumLesson();
        this.title = lesson.getTitle();
        this.description = lesson.getDescription();
    }

    public LessonDto(Long id, Long numLesson, String title, String description) {
        this.id = id;
        this.numLesson = numLesson;
        this.title = title;
        this.description = description;
    }
}
