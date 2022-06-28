package com.malyshev.demojwt.service.impl;

import com.malyshev.demojwt.model.Lesson;
import com.malyshev.demojwt.model.Role;
import com.malyshev.demojwt.model.Status;
import com.malyshev.demojwt.model.User;
import com.malyshev.demojwt.repository.LessonRepository;
import com.malyshev.demojwt.repository.RoleRepository;
import com.malyshev.demojwt.repository.UserRepository;
import com.malyshev.demojwt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LessonRepository lessonRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, LessonRepository lessonRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.lessonRepository = lessonRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCurrentLesson(lessonRepository.findById(1L).orElse(null));

        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users founded", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - {} username founded", result.getUsername());
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if(result == null) {
            log.warn("IN findById - no one find by id: {}", id);
            return null;
        }
        log.info("IN findById - {} username founded", result.getUsername());
        return result;
    }

    @Override
    public List<User> findUsersByLessonNumber(Long number) {
        List<User> users = userRepository.findUsersByCurrentLesson_NumLesson(number);
        return users;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
        log.info("IN update - user successfully updated");
    }

    @Override
    public User setLessonPage(Long id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        if (user.getCurrentLesson().getNumLesson() < lessonRepository.dbSize()) {
            Lesson lesson = lessonRepository.findByNumLesson(user.getCurrentLesson().getNumLesson() + 1);
            user.setCurrentLesson(lesson);
        } else {
            System.out.println("Вы на последнем уроке");
        }
        return user;
    }
}
