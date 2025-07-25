package com.example.demo_junie.service;

import com.example.demo_junie.model.Post;
import java.util.List;

/**
 * Service interface for handling operations related to Posts from JSONPlaceholder API.
 */
public interface PostService {
    
    /**
     * Retrieves all posts from the JSONPlaceholder API.
     *
     * @return a list of all posts
     */
    List<Post> getAllPosts();
    
    /**
     * Retrieves a specific post by its ID.
     *
     * @param id the ID of the post to retrieve
     * @return the post with the specified ID, or null if not found
     */
    Post getPostById(Long id);
    
    /**
     * Creates a new post.
     *
     * @param post the post to create
     * @return the created post with an assigned ID
     */
    Post createPost(Post post);
    
    /**
     * Updates an existing post.
     *
     * @param id the ID of the post to update
     * @param post the updated post data
     * @return the updated post, or null if the post with the specified ID was not found
     */
    Post updatePost(Long id, Post post);
    
    /**
     * Deletes a post by its ID.
     *
     * @param id the ID of the post to delete
     * @return true if the post was successfully deleted, false otherwise
     */
    boolean deletePost(Long id);
}