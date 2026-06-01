package com.psitaland.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger / SpringDoc OpenAPI.
 *
 * Acesse a documentação em: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Psitaland API")
                        .description("API para gestão do plantel de aves do criadouro Psitaland. " +
                                "Permite gerenciar pássaros, espécies, mutações, gaiolas e status.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Psitaland")
                                .email("contato@psitaland.com")));
    }

}
