package dev.luizveronesi.upload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

    @Value("${app.documentation.name}")
    private String moduleName;

    @Value("${app.documentation.version}")
    private String apiVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title(String.format("%s API", StringUtils.capitalize(moduleName))).version(apiVersion));
    }
}