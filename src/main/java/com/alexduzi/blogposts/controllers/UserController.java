package com.alexduzi.blogposts.controllers;

import com.alexduzi.blogposts.models.dto.UserDTO;
import com.alexduzi.blogposts.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> result = userService.findAll();
        return ResponseEntity.ok().body(result);
    }
}
