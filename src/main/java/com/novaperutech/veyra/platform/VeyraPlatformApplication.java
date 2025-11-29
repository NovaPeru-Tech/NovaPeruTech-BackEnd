package com.novaperutech.veyra.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Application entrypoint for the Veyra platform.
 *
 * <p>Bootstraps the Spring Boot application and enables JPA auditing. This class
 * has no state and is only responsible for application startup.</p>
 *
 * <p>Architecture notes:
 * <ul>
 *   <li>Role: Application entrypoint</li>
 *   <li>Side-effects: starts the Spring context and initializes beans</li>
 * </ul>
 * </p>
 */
@SpringBootApplication
@EnableJpaAuditing
public class VeyraPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeyraPlatformApplication.class, args);
    }

}
