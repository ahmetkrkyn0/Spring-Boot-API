package com.example.demo_junie.controller;

import com.example.demo_junie.model.Post;
import com.example.demo_junie.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling Post-related requests.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * Constructor for PostController.
     *
     * @param postService the service to use for post operations
     */
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Retrieves all posts.
     * Accessible by any authenticated user.
     *
     * @return a list of all posts
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * Retrieves a specific post by its ID.
     * Accessible by any authenticated user.
     *
     * @param id the ID of the post to retrieve
     * @return the post with the specified ID, or a 404 Not Found if not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new post.
     * Accessible only by users with ADMIN role.
     *
     * @param post the post to create
     * @return the created post with an assigned ID, or a 400 Bad Request if creation fails
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        if (createdPost != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates an existing post.
     * Accessible only by users with ADMIN role.
     *
     * @param id the ID of the post to update
     * @param post the updated post data
     * @return the updated post, or a 404 Not Found if the post with the specified ID was not found
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a post by its ID.
     * Accessible only by users with ADMIN role.
     *
     * @param id the ID of the post to delete
     * @return a 204 No Content if successful, or a 404 Not Found if the post was not found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean deleted = postService.deletePost(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}