package me.desafio.blog.controllers;

import me.desafio.blog.dto.user.UserCreateDTO;
import me.desafio.blog.dto.user.UserDTO;
import me.desafio.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreateDTO userCreateDTO) {

        String token = authService.register(userCreateDTO.email(), userCreateDTO.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);

    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserCreateDTO userCreateDTO) {
        return ResponseEntity.ok(authService.login(userCreateDTO.email(), userCreateDTO.password()));
    }

}
