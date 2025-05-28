package com.alexduzi.blogposts.services;

import com.alexduzi.blogposts.exceptions.ResourceNotFoundException;
import com.alexduzi.blogposts.models.dto.PostDTO;
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
        User entity = getEntity(id);

        return new UserDTO(entity);
    }

    public UserDTO insert(UserDTO userDTO) {
        User entity = userDTO.toEntity();
        entity = userRepository.save(entity);
        return entity.toDto();
    }

    public UserDTO update(String id, UserDTO userDTO) {
        User entity = getEntity(id);
        entity = entity.copyFrom(userDTO);
        entity = userRepository.save(entity);
        return entity.toDto();
    }

    public List<PostDTO> getUserPosts(String id) {
        User user = getEntity(id);
        return user.getPosts().stream().map(PostDTO::new).toList();
    }

    private User getEntity(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}
