# Making  authentication page using Spring Boot (Without Spring Security)
- Currently just implementing register process , will do Login and logout then session.
- 

# **User Registration & Login Without Spring Security (Fundamental Explanation)**

## Steps to Implement User Registration and Login in a Spring Boot Application

### 1. **Initialize a Spring Boot Project**
- Set up the backend structure using Spring Boot.
- Include necessary dependencies such as Spring Web, Spring Data JPA, and PostgreSQL for database interactions.

### 2. **Configure Database in `application.properties`**
- Set up the PostgreSQL database connection in the `application.properties` file.
- Example configuration:
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
  ```

### 3. **Create a `User` Entity**
- Define the `User` entity class to map user data to the database.
- Example structure:
  ```java
  @Entity
  public class User {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String name;
      private String email;
      private String password;
      // getters and setters
  }
  ```

### 4. **Create a `UserRepository` Interface**
- Create a repository interface extending `JpaRepository` to interact with the database.
- Example:
  ```java
  public interface UserRepository extends JpaRepository<User, Long> {
      Optional<User> findByEmail(String email);
  }
  ```

### 5. **Create a `UserService` Class**
- Create a service class that contains the business logic for user registration and login.
- Example:
  ```java
  @Service
  public class UserService {
      @Autowired
      private UserRepository userRepository;

      public User registerUser(User user) {
          return userRepository.save(user);
      }
      
  }
  ```

[//]: # ()
[//]: # (### 6. **Create a `UserController` Class**)

[//]: # (- Create a REST controller to expose HTTP endpoints for user registration and login.)

[//]: # (- Example:)

[//]: # (  ```java)

[//]: # (  @RestController)

[//]: # (  @RequestMapping&#40;"/api/users"&#41;)

[//]: # (  public class UserController {)

[//]: # (      @Autowired)

[//]: # (      private UserService userService;)

[//]: # ()
[//]: # (      @PostMapping&#40;"/register"&#41;)

[//]: # (      public ResponseEntity<User> registerUser&#40;@RequestBody User user&#41; {)

[//]: # (          User savedUser = userService.registerUser&#40;user&#41;;)

[//]: # (          return ResponseEntity.status&#40;HttpStatus.CREATED&#41;.body&#40;savedUser&#41;;)

[//]: # (      })

[//]: # ()
[//]: # (      @PostMapping&#40;"/login"&#41;)

[//]: # (      public ResponseEntity<String> loginUser&#40;@RequestBody User user&#41; {)

[//]: # (          Optional<User> existingUser = userService.findUserByEmail&#40;user.getEmail&#40;&#41;&#41;;)

[//]: # (          if &#40;existingUser.isPresent&#40;&#41; && existingUser.get&#40;&#41;.getPassword&#40;&#41;.equals&#40;user.getPassword&#40;&#41;&#41;&#41; {)

[//]: # (              return ResponseEntity.ok&#40;"Login successful"&#41;;)

[//]: # (          } else {)

[//]: # (              return ResponseEntity.status&#40;HttpStatus.UNAUTHORIZED&#41;.body&#40;"Invalid credentials"&#41;;)

[//]: # (          })

[//]: # (      })

[//]: # (  })

[//]: # (  ```)

[//]: # ()
[//]: # (### 7. **Use `@RequestBody` for Data Input**)

[//]: # (- Use the `@RequestBody` annotation to accept user input in JSON format and bind it to the `User` entity.)

[//]: # ()
[//]: # (### 8. **Save User to Database**)

[//]: # (- In the `UserService` class, use `userRepository.save&#40;user&#41;` to store the user's data in PostgreSQL after registration.)

[//]: # ()
[//]: # (### 9. **Implement Login API**)

[//]: # (- Implement the login functionality in the `UserController` where you:)

[//]: # (    - Retrieve the user by email.)

[//]: # (    - Verify the password.)

[//]: # (    - Return an authentication status &#40;success or failure&#41;.)

[//]: # ()
[//]: # (### 10. **Test with Postman or API Client**)

[//]: # (- Use Postman or another API client to test your registration and login functionality by sending POST requests to the appropriate endpoints:)

[//]: # (    - `POST /api/users/register` to register a new user.)

[//]: # (    - `POST /api/users/login` to login with an existing user.)
