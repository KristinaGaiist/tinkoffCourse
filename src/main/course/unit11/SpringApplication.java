package unit11;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"unit8.service", "unit8.repository", "unit11.controller", "unit11.service", "unit11.repository"})
public class SpringApplication {

    public static void main(String... args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }
}