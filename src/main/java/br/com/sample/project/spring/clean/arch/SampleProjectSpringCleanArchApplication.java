package br.com.sample.project.spring.clean.arch;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleProjectSpringCleanArchApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SampleProjectSpringCleanArchApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Books Application API").description(
                        "This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."));
    }

}
