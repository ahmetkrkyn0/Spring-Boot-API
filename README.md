# Spring Boot API

This repository contains a Spring Boot application that provides a secure REST API with JWT authentication.

## Project Structure

The main project is located in the [demo-junie](./demo-junie) directory.

## Features

- RESTful API for managing posts
- JWT-based authentication and authorization
- Role-based access control
- Integration with JSONPlaceholder external API
- Comprehensive test coverage

## Technologies Used

- Java 11+
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Maven
- RESTful API design principles

## Getting Started

For detailed information about the API endpoints, authentication, and how to run the application, please refer to the [detailed README](./demo-junie/README.md) in the demo-junie directory.

### Quick Start

1. Clone the repository
2. Navigate to the project directory
   ```
   cd demo-junie
   ```
3. Run the application using Maven:
   ```
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8080`

## API Documentation

The API provides endpoints for:
- User registration and authentication
- CRUD operations for posts

For complete API documentation, see the [detailed README](./demo-junie/README.md).

## Security

The application implements JWT-based authentication with role-based access control:
- Regular users can view posts
- Admin users can create, update, and delete posts

## License

This project is open source and available under the [MIT License](LICENSE).

## Contact

For questions or feedback, please open an issue in this repository.