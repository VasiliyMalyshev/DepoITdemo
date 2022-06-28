package com.malyshev.demojwt.repository;

import com.malyshev.demojwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findUsersByCurrentLesson_NumLesson(Long CurrentLesson_NumLesson);
}