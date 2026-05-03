```java
package com.example.autoGithubCommit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Auto GitHub Commit Spring Boot application.
 * This class bootstraps and launches the application using {@link SpringApplication}.
 */
@SpringBootApplication
public class AutoGithubCommitApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(AutoGithubCommitApplication.class, args);
    }

}
```