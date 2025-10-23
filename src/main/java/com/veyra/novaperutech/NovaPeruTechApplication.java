package com.veyra.novaperutech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NovaPeruTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovaPeruTechApplication.class, args);
    }

}
