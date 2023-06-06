package me.liuzeyu.aimusic.auth.controller;

import lombok.extern.slf4j.Slf4j;
import me.liuzeyu.aimusic.auth.api.beans.ApiResponse;
import me.liuzeyu.aimusic.auth.api.beans.JwtAuthenticationResponse;
import me.liuzeyu.aimusic.auth.api.beans.LoginRequest;
import me.liuzeyu.aimusic.auth.api.beans.SignUpRequest;
import me.liuzeyu.aimusic.auth.dao.entity.User;
import me.liuzeyu.aimusic.auth.service.intf.UserService;
import me.liuzeyu.aimusic.music.api.beans.MusicInfo;
import me.liuzeyu.aimusic.music.api.beans.UpdateMusicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("user")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("login user: data={}", loginRequest);
        String token = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest payload) {
        log.info("creating user {}", payload.getUsername());

        User user = User
                .builder()
                .username(payload.getUsername())
                .email(payload.getEmail())
                .password(payload.getPassword())
                .active(true)
                .build();

        userService.registerUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true,"User registered successfully"));
    }

}
