package com.task_management_app.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<?> createUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        return ResponseEntity.ok(userRepo.save(user));
    }

    public ResponseEntity<?> updateUser(String id, User user) {
        Optional<User> existingUserOptional = userRepo.findById(id);

        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User existingUser = existingUserOptional.get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setSecondName(user.getSecondName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return ResponseEntity.ok(userRepo.save(existingUser));
    }

    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    public ResponseEntity<?> getUserById(String id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user.get());
    }

    public ResponseEntity<?> deleteUser(String id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok("User successfully deleted");
    }
}
