# JSONPlaceholder Posts API

This is a Spring Boot application that provides a CRUD REST API for posts using the JSONPlaceholder service.

## API Endpoints

### Get All Posts
Retrieves a list of all posts.

- **URL**: `/api/posts`
- **Method**: GET
- **Response**: 200 OK
  ```json
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
  ```

### Get Post by ID
Retrieves a specific post by its ID.

- **URL**: `/api/posts/{id}`
- **Method**: GET
- **URL Parameters**: `id=[Long]` where `id` is the ID of the post to retrieve
- **Response**: 200 OK
  ```json
  {
    "id": 1,
    "userId": 1,
    "title": "Post title",
    "body": "Post body"
  }
  ```
- **Error Response**: 404 Not Found if the post with the specified ID does not exist

### Create Post
Creates a new post.

- **URL**: `/api/posts`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "userId": 1,
    "title": "New post title",
    "body": "New post body"
  }
  ```
- **Response**: 201 Created
  ```json
  {
    "id": 101,
    "userId": 1,
    "title": "New post title",
    "body": "New post body"
  }
  ```
- **Error Response**: 400 Bad Request if the post could not be created

### Update Post
Updates an existing post.

- **URL**: `/api/posts/{id}`
- **Method**: PUT
- **URL Parameters**: `id=[Long]` where `id` is the ID of the post to update
- **Request Body**:
  ```json
  {
    "userId": 1,
    "title": "Updated post title",
    "body": "Updated post body"
  }
  ```
- **Response**: 200 OK
  ```json
  {
    "id": 1,
    "userId": 1,
    "title": "Updated post title",
    "body": "Updated post body"
  }
  ```
- **Error Response**: 404 Not Found if the post with the specified ID does not exist

### Delete Post
Deletes a post by its ID.

- **URL**: `/api/posts/{id}`
- **Method**: DELETE
- **URL Parameters**: `id=[Long]` where `id` is the ID of the post to delete
- **Response**: 204 No Content
- **Error Response**: 404 Not Found if the post with the specified ID does not exist

## Authentication and Authorization

This API is secured using JWT (JSON Web Token) authentication. To access the protected endpoints, you need to:

1. Register a new user or use one of the demo users
2. Obtain a JWT token by logging in
3. Include the JWT token in the Authorization header of your requests

### Authentication Endpoints

#### Register
Creates a new user account.

- **URL**: `/api/auth/register`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "username": "newuser",
    "password": "password123",
    "email": "newuser@example.com"
  }
  ```
- **Response**: 200 OK
  ```
  User registered successfully!
  ```
- **Error Response**: 400 Bad Request if the username or email is already taken

#### Login
Authenticates a user and returns a JWT token.

- **URL**: `/api/auth/login`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```
- **Response**: 200 OK
  ```json
  {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "type": "Bearer",
    "id": 1,
    "username": "admin",
    "email": "admin@example.com",
    "roles": ["ROLE_USER", "ROLE_ADMIN"]
  }
  ```

### Using the JWT Token

To access protected endpoints, include the JWT token in the Authorization header of your requests:

```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

### Authorization Rules

The API implements role-based access control:

- **GET** operations (`/api/posts`, `/api/posts/{id}`) require any authenticated user
- **POST**, **PUT**, and **DELETE** operations require ADMIN role

### Demo Users

For testing purposes, the application includes two demo users:

1. **Admin User**
   - Username: admin
   - Password: admin123
   - Roles: ROLE_USER, ROLE_ADMIN

2. **Regular User**
   - Username: user
   - Password: user123
   - Roles: ROLE_USER

## Notes

- This API interacts with the JSONPlaceholder service (https://jsonplaceholder.typicode.com).
- JSONPlaceholder is a fake online REST API for testing and prototyping.
- The JSONPlaceholder service doesn't actually create, update, or delete resources on its server, but it simulates these operations and returns appropriate responses.

## Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application using Maven:
   ```
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8080`

## Testing

The application includes unit tests for the controller layer. To run the tests:

```
./mvnw test
```