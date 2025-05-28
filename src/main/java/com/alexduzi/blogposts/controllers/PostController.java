package com.alexduzi.blogposts.controllers;

import com.alexduzi.blogposts.models.dto.PostDTO;
import com.alexduzi.blogposts.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> findAll() {
        List<PostDTO> result = postService.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> findById(@PathVariable String id) {
        PostDTO result = postService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/titlesearch")
    public ResponseEntity<List<PostDTO>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
        List<PostDTO> result = postService.findByTitle(text);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<PostDTO> insert(@RequestBody PostDTO postDTO) {
        PostDTO result = postService.insert(postDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable String id, @RequestBody PostDTO postDTO) {
        PostDTO result = postService.update(id, postDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable String id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
