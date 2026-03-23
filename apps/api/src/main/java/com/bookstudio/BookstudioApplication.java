package com.bookstudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "BookStudio API",
        version = "1.0.0",
        description = "Backend API supporting BookStudio's library management panel, enabling efficient administrative workflows.",
        contact = @Contact(
            name = "BookStudio Support",
            email = "bookstudio.library@gmail.com"
        ),
        license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
    )
)
public class BookstudioApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookstudioApplication.class, args);
    }
}
