package com.malyshev.demojwt.rest;

import com.malyshev.demojwt.dto.AdminDto;
import com.malyshev.demojwt.dto.LessonDto;
import com.malyshev.demojwt.model.Lesson;
import com.malyshev.demojwt.model.User;
import com.malyshev.demojwt.security.jwt.JwtTokenProvider;
import com.malyshev.demojwt.service.LessonService;
import com.malyshev.demojwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserRestControllerV1 {
    private final UserService userService;
    private final LessonService lessonService;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public UserRestControllerV1(UserService userService, LessonService lessonService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.lessonService = lessonService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AdminDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        AdminDto result = AdminDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/tokenId")
    public ResponseEntity<AdminDto> getUserByTokenId(@RequestHeader("authorization") String token) {
        User user = userService.findByUsername(jwtTokenProvider.getUsernameFromToken(token.substring(7, token.length())));
        System.out.println(user.getUsername());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        AdminDto result = AdminDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> user = userService.getAll();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/lesson/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable(name = "id") Long id) {
        Lesson lesson = lessonService.findById(id);
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/lesson/getAll")
    public ResponseEntity<List<LessonDto>> getAllLessons(@RequestHeader("authorization") String token) {
        User user = userService.findByUsername(jwtTokenProvider.getUsernameFromToken(token.substring(7, token.length())));
        List<LessonDto> lesson = lessonService.findLessonsByUser(userService.findById(user.getId()));
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/lesson/filtered/{idNumber}")
    public ResponseEntity<List<User>> selectedUsers(@PathVariable(name = "idNumber") Long idNumber, @RequestHeader("authorization") String token) {
        User user = userService.findByUsername(jwtTokenProvider.getUsernameFromToken(token.substring(7, token.length())));
        List<User> users = userService.findUsersByLessonNumber(idNumber);
        users.remove(user);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}