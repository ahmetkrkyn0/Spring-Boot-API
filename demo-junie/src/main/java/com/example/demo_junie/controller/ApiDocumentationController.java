package com.example.demo_junie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for API documentation.
 * Provides a simple HTML page with information about the available API endpoints.
 */
@Controller
@RequestMapping("/api-docs")
public class ApiDocumentationController {

    /**
     * Returns an HTML page with API documentation.
     *
     * @return HTML content with API documentation
     */
    @GetMapping(produces = "text/html")
    @ResponseBody
    public String getApiDocumentation() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Posts API Documentation</title>
                    <style>
                        body { font-family: Arial, sans-serif; margin: 20px; }
                        h1 { color: #333; }
                        h2 { color: #555; margin-top: 30px; }
                        .endpoint { background-color: #f5f5f5; padding: 15px; margin: 10px 0; border-radius: 5px; }
                        .method { font-weight: bold; color: #0066cc; }
                        .path { font-family: monospace; }
                        .description { margin: 10px 0; }
                        .params { margin-left: 20px; }
                        .response { margin-left: 20px; }
                        pre { background-color: #eee; padding: 10px; border-radius: 3px; overflow-x: auto; }
                    </style>
                </head>
                <body>
                    <h1>Posts API Documentation</h1>
                    <p>This API provides endpoints for managing posts using the JSONPlaceholder service.</p>
                    
                    <h2>Endpoints</h2>
                    
                    <div class="endpoint">
                        <div><span class="method">GET</span> <span class="path">/api/posts</span></div>
                        <div class="description">Retrieves a list of all posts.</div>
                        <div class="response">
                            <strong>Response:</strong> 200 OK
                            <pre>
                [
                  {
                    "id": 1,
                    "userId": 1,
                    "title": "Post title",
                    "body": "Post body"
                  },
                  {
                    "id": 2,
                    "userId": 1,
                    "title": "Another post title",
                    "body": "Another post body"
                  }
                ]
                            </pre>
                        </div>
                    </div>
                    
                    <div class="endpoint">
                        <div><span class="method">GET</span> <span class="path">/api/posts/{id}</span></div>
                        <div class="description">Retrieves a specific post by its ID.</div>
                        <div class="params">
                            <strong>Path Parameters:</strong>
                            <ul>
                                <li><code>id</code> - The ID of the post to retrieve</li>
                            </ul>
                        </div>
                        <div class="response">
                            <strong>Response:</strong> 200 OK
                            <pre>
                {
                  "id": 1,
                  "userId": 1,
                  "title": "Post title",
                  "body": "Post body"
                }
                            </pre>
                            <strong>Error Response:</strong> 404 Not Found if the post with the specified ID does not exist
                        </div>
                    </div>
                    
                    <div class="endpoint">
                        <div><span class="method">POST</span> <span class="path">/api/posts</span></div>
                        <div class="description">Creates a new post.</div>
                        <div class="params">
                            <strong>Request Body:</strong>
                            <pre>
                {
                  "userId": 1,
                  "title": "New post title",
                  "body": "New post body"
                }
                            </pre>
                        </div>
                        <div class="response">
                            <strong>Response:</strong> 201 Created
                            <pre>
                {
                  "id": 101,
                  "userId": 1,
                  "title": "New post title",
                  "body": "New post body"
                }
                            </pre>
                            <strong>Error Response:</strong> 400 Bad Request if the post could not be created
                        </div>
                    </div>
                    
                    <div class="endpoint">
                        <div><span class="method">PUT</span> <span class="path">/api/posts/{id}</span></div>
                        <div class="description">Updates an existing post.</div>
                        <div class="params">
                            <strong>Path Parameters:</strong>
                            <ul>
                                <li><code>id</code> - The ID of the post to update</li>
                            </ul>
                            <strong>Request Body:</strong>
                            <pre>
                {
                  "userId": 1,
                  "title": "Updated post title",
                  "body": "Updated post body"
                }
                            </pre>
                        </div>
                        <div class="response">
                            <strong>Response:</strong> 200 OK
                            <pre>
                {
                  "id": 1,
                  "userId": 1,
                  "title": "Updated post title",
                  "body": "Updated post body"
                }
                            </pre>
                            <strong>Error Response:</strong> 404 Not Found if the post with the specified ID does not exist
                        </div>
                    </div>
                    
                    <div class="endpoint">
                        <div><span class="method">DELETE</span> <span class="path">/api/posts/{id}</span></div>
                        <div class="description">Deletes a post by its ID.</div>
                        <div class="params">
                            <strong>Path Parameters:</strong>
                            <ul>
                                <li><code>id</code> - The ID of the post to delete</li>
                            </ul>
                        </div>
                        <div class="response">
                            <strong>Response:</strong> 204 No Content
                            <strong>Error Response:</strong> 404 Not Found if the post with the specified ID does not exist
                        </div>
                    </div>
                    
                    <h2>Models</h2>
                    
                    <h3>Post</h3>
                    <pre>
                {
                  "id": Long,       // The unique identifier of the post
                  "userId": Long,   // The ID of the user who created the post
                  "title": String,  // The title of the post
                  "body": String    // The content of the post
                }
                    </pre>
                    
                    <h2>Notes</h2>
                    <ul>
                        <li>This API interacts with the JSONPlaceholder service (https://jsonplaceholder.typicode.com).</li>
                        <li>JSONPlaceholder is a fake online REST API for testing and prototyping.</li>
                        <li>The JSONPlaceholder service doesn't actually create, update, or delete resources on its server, but it simulates these operations and returns appropriate responses.</li>
                    </ul>
                </body>
                </html>
                """;
    }
    
    /**
     * Redirects the root path to the API documentation.
     *
     * @return a redirect to the API documentation page
     */
    @GetMapping("/")
    public String redirectToApiDocs() {
        return "redirect:/api-docs";
    }
}