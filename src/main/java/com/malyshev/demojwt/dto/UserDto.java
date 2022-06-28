package com.malyshev.demojwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.malyshev.demojwt.model.Lesson;
import com.malyshev.demojwt.model.User;
import lombok.Data;

import java.nio.file.LinkOption;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    private Long currentLesson;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setCurrentLesson(user.getCurrentLesson().getNumLesson());
        return userDto;
    }
}
