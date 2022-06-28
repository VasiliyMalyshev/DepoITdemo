package com.malyshev.demojwt.rest;

import com.malyshev.demojwt.model.Lesson;
import com.malyshev.demojwt.model.User;
import com.malyshev.demojwt.security.jwt.JwtTokenProvider;
import com.malyshev.demojwt.service.LessonService;
import com.malyshev.demojwt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/admin")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final LessonService lessonService;
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AdminRestControllerV1(UserService userService, LessonService lessonService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.lessonService = lessonService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/lesson/update")
    public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson) {
        lessonService.updateLesson(lesson);
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/lesson/create")
    public ResponseEntity<Lesson> createLesson() {
        Lesson lesson = new Lesson(1000L, "Пустое имя", "Пустое описание");
        lessonService.addLesson(lesson);
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/users/{id}/updateCurrentLesson")
    public ResponseEntity<User> updateUserCurrentLesson(@PathVariable(name = "id") Long id) {
        User user = userService.setLessonPage(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}