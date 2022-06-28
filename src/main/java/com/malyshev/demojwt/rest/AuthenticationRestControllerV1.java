package com.malyshev.demojwt.rest;

import com.malyshev.demojwt.dto.AuthenticationRequestDto;
import com.malyshev.demojwt.dto.TokenDto;
import com.malyshev.demojwt.model.User;
import com.malyshev.demojwt.security.jwt.JwtTokenProvider;
import com.malyshev.demojwt.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/")
public class AuthenticationRestControllerV1 {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationRestControllerV1(@Lazy AuthenticationManager authenticationManager, @Lazy JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }
    @PostMapping(value = "authenticate")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + " not found");
            }

            TokenDto tokenDto = new TokenDto(jwtTokenProvider.createToken(username, user.getRoles()));

            return ResponseEntity.ok(tokenDto);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "addNewUser")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userService.register(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
