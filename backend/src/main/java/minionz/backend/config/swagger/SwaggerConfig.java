package minionz.backend.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(List.of(new Server().url("/api"))); // 기본 경로 설정

    }

    private Info apiInfo() {
        return new Info()
                .title("Calit Swagger")
                .description("Calit에 관한 REST API")
                .version("1.0.0");
    }
}