package com.alexduzi.blogposts.services;

import com.alexduzi.blogposts.exceptions.ResourceNotFoundException;
import com.alexduzi.blogposts.models.dto.UserDTO;
import com.alexduzi.blogposts.models.entities.User;
import com.alexduzi.blogposts.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    public UserDTO findById(String id) {
        User entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserDTO(entity);
    }

    public UserDTO insert(UserDTO userDTO) {
        User entity = userDTO.toEntity();
        entity = userRepository.save(entity);
        return entity.toDto();
    }
}
