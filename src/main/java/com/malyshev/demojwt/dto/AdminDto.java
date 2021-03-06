package com.malyshev.demojwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.malyshev.demojwt.model.Role;
import com.malyshev.demojwt.model.User;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    private List<Role> roles;


    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return user;
    }

    public static AdminDto fromUser(User user) {
        AdminDto userDto = new AdminDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
