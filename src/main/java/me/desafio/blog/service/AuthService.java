package me.desafio.blog.service;

import me.desafio.blog.dto.user.UserDTO;
import me.desafio.blog.entities.UserEntity;
import me.desafio.blog.infra.JwtUtil;
import me.desafio.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO login(String email, String password) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return new UserDTO(user.getEmail(), user.getId(), jwtUtil.generateToken(email, user.getId()));
    }

    public String register(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password must not be empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must have at least 6 characters");
        }
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        String token = jwtUtil.generateToken(email, user.getId());
        return token;
    }

    public UserDTO getUser(String email) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = userOptional.get();
        return new UserDTO(user.getEmail(), user.getId(), jwtUtil.generateToken(email, user.getId()));
    }

    public UserDTO getUserById(String id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = userOptional.get();
        return new UserDTO(user.getEmail(), user.getId(), jwtUtil.generateToken(user.getEmail(), user.getId()));
    }

    public void deleteUser(String email) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = userOptional.get();
        userRepository.delete(user);
    }

    public void deleteUserById(String id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = userOptional.get();
        userRepository.delete(user);
    }

    public UserDTO updateUser(String email, String newEmail, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = userOptional.get();
        return getUserDTO(newEmail, newPassword, user);
    }

    public UserDTO updateUserById(String id, String newEmail, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = userOptional.get();
        return getUserDTO(newEmail, newPassword, user);
    }

    private UserDTO getUserDTO(String newEmail, String newPassword, UserEntity user) {
        if (newEmail != null && !newEmail.isEmpty()) {
            user.setEmail(newEmail);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        userRepository.save(user);
        return new UserDTO(user.getEmail(), user.getId(), jwtUtil.generateToken(user.getEmail(), user.getId()));
    }

    public UserDTO updatePassword(String email, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = userOptional.get();
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        userRepository.save(user);
        return new UserDTO(user.getEmail(), user.getId(), jwtUtil.generateToken(user.getEmail(), user.getId()));
    }

    public UserDTO updatePasswordById(String id, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = userOptional.get();
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        userRepository.save(user);
        return new UserDTO(user.getEmail(), user.getId(), jwtUtil.generateToken(user.getEmail(), user.getId()));
    }


}
