package com.example.demo_junie.controller;

import com.example.demo_junie.model.Post;
import com.example.demo_junie.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the PostController class.
 */
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private Post testPost;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        // Create a test post
        testPost = new Post(1L, 1L, "Test Title", "Test Body");
    }

    @Test
    @WithMockUser
    public void testGetAllPosts() {
        // Arrange
        List<Post> posts = Arrays.asList(
            new Post(1L, 1L, "Title 1", "Body 1"),
            new Post(2L, 1L, "Title 2", "Body 2")
        );
        when(postService.getAllPosts()).thenReturn(posts);

        // Act
        ResponseEntity<List<Post>> response = postController.getAllPosts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(postService, times(1)).getAllPosts();
    }

    @Test
    @WithMockUser
    public void testGetPostById_Found() {
        // Arrange
        when(postService.getPostById(1L)).thenReturn(testPost);

        // Act
        ResponseEntity<Post> response = postController.getPostById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPost, response.getBody());
        verify(postService, times(1)).getPostById(1L);
    }

    @Test
    @WithMockUser
    public void testGetPostById_NotFound() {
        // Arrange
        when(postService.getPostById(99L)).thenReturn(null);

        // Act
        ResponseEntity<Post> response = postController.getPostById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(postService, times(1)).getPostById(99L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreatePost_Success() {
        // Arrange
        Post newPost = new Post(null, 1L, "New Title", "New Body");
        Post createdPost = new Post(1L, 1L, "New Title", "New Body");
        when(postService.createPost(newPost)).thenReturn(createdPost);

        // Act
        ResponseEntity<Post> response = postController.createPost(newPost);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPost, response.getBody());
        verify(postService, times(1)).createPost(newPost);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreatePost_Failure() {
        // Arrange
        Post newPost = new Post(null, 1L, "New Title", "New Body");
        when(postService.createPost(newPost)).thenReturn(null);

        // Act
        ResponseEntity<Post> response = postController.createPost(newPost);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(postService, times(1)).createPost(newPost);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdatePost_Success() {
        // Arrange
        Post updatedPost = new Post(1L, 1L, "Updated Title", "Updated Body");
        when(postService.updatePost(eq(1L), any(Post.class))).thenReturn(updatedPost);

        // Act
        ResponseEntity<Post> response = postController.updatePost(1L, testPost);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPost, response.getBody());
        verify(postService, times(1)).updatePost(eq(1L), any(Post.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdatePost_NotFound() {
        // Arrange
        when(postService.updatePost(eq(99L), any(Post.class))).thenReturn(null);

        // Act
        ResponseEntity<Post> response = postController.updatePost(99L, testPost);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(postService, times(1)).updatePost(eq(99L), any(Post.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeletePost_Success() {
        // Arrange
        when(postService.deletePost(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = postController.deletePost(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(postService, times(1)).deletePost(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeletePost_NotFound() {
        // Arrange
        when(postService.deletePost(99L)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = postController.deletePost(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(postService, times(1)).deletePost(99L);
    }
}