# Making  authentication page using Spring Boot (Without Spring Security)
- Currently just implementing register process , will do Login and logout then session.

# **User Registration & Login Without Spring Security (Fundamental Explanation)**

## Steps to Implement User Registration and Login in a Spring Boot Application

### 1. **Initialize a Spring Boot Project**
- Set up the backend structure using Spring Boot.
- Include necessary dependencies such as Spring Web, Spring Data JPA, and PostgreSQL for database interactions.

### 2. **Configure Database in `application.properties`**
- Set up the PostgreSQL database connection in the `application.properties` file.
- Example configuration:
  ```properties
  spring.application.name=Register_Login
  
  server.port=8080
  
  #DB Connection
  spring.datasource.username=userName
  spring.datasource.password=password
  
  spring.datasource.url=jdbc:postgresql://localhost:5432/auth01
  
  #Auto -update or create db schema
  #Automatically create or update tables based on entity mapping
  # Ensure the schema is updated based on JPA entities
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  
  # show SQL queries in logs
  
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.format_sql=true
  
  logging.level.org.hibernate=DEBUG
  logging.level.org.springframework.orm.jpa=DEBUG
  ```

### 3. **Create a `User` Entity**
- Define the `User` entity class to map user data to the database.
  - Example structure:
    ```java

    @Entity
    @Table(name="authUser")
    public class userModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastName;
      // getter setters ..
    }
    ```

### 4. **Create a `UserRepository` Interface**
- Create a repository interface extending `JpaRepository` to interact with the database.
- Example:
  ```java
  public interface userRepo extends JpaRepository<userModel, int> {
      
  }
  ```

### 5. **Create a `UserService` Class**
- Create a service class that contains the business logic for user registration. (also for login, later)
- Example:
  ```java
  @Service
  public class userService {
  @Autowired
  private userRepo repo;

    public userModel registerUser(userModel user){
        return repo.save(user);
        }
    }
  ```

- Commented this part below, will uncomment these after Completing those steps and update as my experience while implementing those.


### 6. **Create a `UserController` Class**

- Create a REST controller to expose HTTP endpoints for user registration and login.

- Example:

  ```java

  @RestController
  public class userController {

      @Autowired
      private userService userService;
      
      @PostMapping("/register")
       // find proper way to return JSON instead of String
      public ResponseEntity<String> registerUser(@RequestBody User user) {
              try {
            userService.registerUser(user);
            return ResponseEntity.ok("{" +
                    "\"message\":" +
                    " \"User Registered Successfully\"" +
                    "}"
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration Failed");
        }
      }
  }

  ```


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


