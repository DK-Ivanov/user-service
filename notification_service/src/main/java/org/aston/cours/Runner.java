package org.aston.cours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс программы.
 */
@SpringBootApplication(scanBasePackages = "org/aston/cours")
public class Runner {
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}