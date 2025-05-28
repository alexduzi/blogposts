package com.alexduzi.blogposts.services;

import com.alexduzi.blogposts.exceptions.ResourceNotFoundException;
import com.alexduzi.blogposts.models.dto.PostDTO;
import com.alexduzi.blogposts.models.entities.Post;
import com.alexduzi.blogposts.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(PostDTO::new).toList();
    }

    public PostDTO findById(String id) {
        Post entity = getEntity(id);

        return new PostDTO(entity);
    }

    public List<PostDTO> findByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title).stream().map(PostDTO::new).toList();
    }

    public List<PostDTO> fullSearch(String text, String start, String end) {
        Instant startMoment = convertMoment(start, Instant.ofEpochMilli(0L));
        Instant endMoment = convertMoment(start, Instant.now());

        return postRepository.fullSearch(text, startMoment, endMoment).stream().map(PostDTO::new).toList();
    }

    private Instant convertMoment(String originalString, Instant alternative) {
        try {
            return Instant.parse(originalString);
        } catch (DateTimeParseException e) {
            return alternative;
        }
    }

    public PostDTO insert(PostDTO postDTO) {
        Post entity = postDTO.toEntity();
        entity = postRepository.save(entity);
        return entity.toDto();
    }

    public PostDTO update(String id, PostDTO postDTO) {
        Post entity = getEntity(id);
        entity = entity.copyFrom(postDTO);
        entity = postRepository.save(entity);
        return entity.toDto();
    }

    private Post getEntity(String id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    public void delete(String id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found");
        }
        postRepository.deleteById(id);
    }
}
