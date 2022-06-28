package com.malyshev.demojwt.service;

import com.malyshev.demojwt.model.User;

import java.util.List;

public interface UserService {

    User register(User user);
    void updateUser(User user);
    void delete(Long id);
    List<User> getAll();
    User findByUsername(String username);
    User findById(Long id);
    List<User> findUsersByLessonNumber(Long number);
    public User setLessonPage(Long id);
}
