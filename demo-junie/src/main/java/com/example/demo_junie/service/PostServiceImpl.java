package com.example.demo_junie.service;

import com.example.demo_junie.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the PostService interface that interacts with the JSONPlaceholder API.
 */
@Service
public class PostServiceImpl implements PostService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    /**
     * Constructor for PostServiceImpl.
     *
     * @param restTemplate the RestTemplate to use for making HTTP requests
     * @param baseUrl the base URL of the JSONPlaceholder API
     */
    @Autowired
    public PostServiceImpl(RestTemplate restTemplate, 
                          @Value("${jsonplaceholder.api.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<Post> getAllPosts() {
        try {
            ResponseEntity<Post[]> response = restTemplate.getForEntity(
                    baseUrl + "/posts", 
                    Post[].class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
            return Collections.emptyList();
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error fetching all posts: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Post getPostById(Long id) {
        try {
            ResponseEntity<Post> response = restTemplate.getForEntity(
                    baseUrl + "/posts/" + id, 
                    Post.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
            return null;
        } catch (HttpClientErrorException.NotFound e) {
            // Post not found
            return null;
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error fetching post with ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public Post createPost(Post post) {
        try {
            ResponseEntity<Post> response = restTemplate.postForEntity(
                    baseUrl + "/posts", 
                    post, 
                    Post.class
            );
            
            if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
            return null;
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error creating post: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Post updatePost(Long id, Post post) {
        try {
            // Ensure the ID in the path matches the ID in the post object
            post.setId(id);
            
            HttpEntity<Post> requestEntity = new HttpEntity<>(post);
            ResponseEntity<Post> response = restTemplate.exchange(
                    baseUrl + "/posts/" + id, 
                    HttpMethod.PUT, 
                    requestEntity, 
                    Post.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
            return null;
        } catch (HttpClientErrorException.NotFound e) {
            // Post not found
            return null;
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error updating post with ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deletePost(Long id) {
        try {
            restTemplate.delete(baseUrl + "/posts/" + id);
            // JSONPlaceholder doesn't actually delete resources, but returns a 200 OK status
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            // Post not found
            return false;
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error deleting post with ID " + id + ": " + e.getMessage());
            return false;
        }
    }
}