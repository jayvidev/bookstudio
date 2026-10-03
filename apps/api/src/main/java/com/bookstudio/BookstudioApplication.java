package com.bookstudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

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
    ),
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Access token from POST /auth/login or POST /auth/demo"
)
public class BookstudioApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookstudioApplication.class, args);
    }
}
