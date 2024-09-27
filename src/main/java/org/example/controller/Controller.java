package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor

@RestController
public class Controller {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable String id) {
        return userRepository.findById(UUID.fromString(id)).orElse(new User(UUID.randomUUID(), "tester"));
    }

    @PostMapping("/user/{name}")
    public User createUser(@PathVariable String name) {
        return userRepository.save(new User(name));
    }

    @PutMapping("/user/{id}&{name}")
    public User createUser(@PathVariable String id, @PathVariable String name) {
        Optional<User> existedUser = userRepository.findById(UUID.fromString(id));
        if(existedUser.isPresent()){
            User user = existedUser.get();
            user.setName(name);

            return userRepository.insert(user);
        }
        return new User(UUID.randomUUID(), "tester");
    }

    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable String id) {
        userRepository.deleteById(UUID.fromString(id));
    }
}