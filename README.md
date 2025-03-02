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
- Add logic to handle input validation
- Handle register logic here 
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
- use <Map<String,String> to return JSON, or change receive format in React(Frontend).
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


### 7. **Use `@RequestBody` for Data Input**

- Use the `@RequestBody` annotation to accept user input in JSON format and bind it to the `User` entity.


### 8. **Save User to Database**

- In the `UserService` class, use `userRepository.save(user)` to store the user's data in PostgreSQL after registration.
- handle register logic in userService


# Hasing Password
- hashing password before saving in db, using SHA-256 (although it's not much secure {bruteFore} but just for learning how to hash before storing in spring Boot).
- use MessageDigest class for hashing (SHA-256)
- Use bCrypt, it's more secure (Spring Security).


[//]: # ()
[//]: # (### 9. **Implement Login API**)

[//]: # (- Implement the login functionality in the `UserController` where you:)

[//]: # (    - Retrieve the user by email.)

[//]: # (    - Verify the password.)

[//]: # (    - Return an authentication status &#40;success or failure&#41;.)

[//]: # ()


# **** Database Section *****
- first quesry is significantly slower(181-211ms) while all other after that are much faster(3-12,13ms).
- It's due to caching , Hibernate(JPA) and some other factors...
- To improve this I can run a dummy query on apllication start to warm up caches.

- Add an index to email and userName (field used in login) as these are now unique.. for faster look-Up, Increases storage space.


# Login







# Session Management

- Intilize HttpSession object 
- Now set required data using ``` .setAttribute("userId",(int)foundUser.getId());``` and all other required like ```.setAttribute("name",foundUser.getUserName()) ```
  
- Code 1 (Working)
```
session.setAttribute("userId", foundUser.getId());

Object sessionUserId = session.getAttribute("userId");

System.out.println("Session ID: " + session.getId());

response.put("message", "Login Successful");
return ResponseEntity.ok(response);
```
- Code 2 (Not working)

```
session.setAttribute("userId", foundUser.getId());

System.out.println("Session ID: " + session.getId());

response.put("message", "Login Successful");
return ResponseEntity.ok(response);
```

# All Rest API Endpoints


# User Controller API Documentation

This document provides a brief overview of the REST API endpoints available in the `userController` class.


## Endpoints

### 1. Home
- **URL:** `/`
- **Method:** `GET`
- **Description:** Returns a simple "home" string.
- **Response:** `String`

### 2. Register User
- **URL:** `/register`
- **Method:** `POST`
- **Description:** Registers a new user.
- **Request Body:** `userModel` object containing user details.
- **Response:** `ResponseEntity<Map<String, String>>`

### 3. Login User
- **URL:** `/login`
- **Method:** `POST`
- **Description:** Authenticates a user and logs them in.
- **Request Body:** `userModel` object containing login credentials.
- **Response:** `ResponseEntity<Map<String, String>>`

### 4. Update User Profile
- **URL:** `/updateProfile`
- **Method:** `PATCH`
- **Description:** Updates the profile of the logged-in user.
- **Request Body:** `userDTO` object containing updated user details.
- **Response:** `ResponseEntity<Map<String, String>>`

### 5. Get Session Data
- **URL:** `/session-data`
- **Method:** `GET`
- **Description:** Retrieves session data for the logged-in user.
- **Response:** `Map<String, Object>`

### 6. Logout User
- **URL:** `/logout`
- **Method:** `POST`
- **Description:** Logs out the currently logged-in user.
- **Response:** `ResponseEntity<Map<String, String>>`

### 7. About
- **URL:** `/about`
- **Method:** `GET`
- **Description:** Returns a simple "about" string.
- **Response:** `String`

### 8. Contact
- **URL:** `/contact`
- **Method:** `GET`
- **Description:** Returns a simple "contact" string.
- **Response:** `String`

### 9. Delete User
- **URL:** `/deleteUser`
- **Method:** `DELETE`
- **Description:** Deletes a user by their ID.
- **Request Body:** `int` representing the user ID.
- **Response:** `ResponseEntity<Map<String, String>>`

## Notes
- The `userService` class handles the business logic for each endpoint.
- Session management is used to track logged-in users.
- Some endpoints (e.g., `/session-data`, `/logout`) may require adjustments for compatibility with frontend frameworks like React.



# TODO

- Change hash password check validation, using ``verify``
- Write Login Implementation details
