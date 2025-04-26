package it.micael.vanini.cinemille.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private final String applicationName;
    private final String apiVersion;

    public SwaggerConfig(
            @Value("${spring.application.name}") String applicationName,
            @Value("${it.micael.vanini.cinemille.api-version}") String apiVersion
    ) {
        this.applicationName = applicationName;
        this.apiVersion = apiVersion;
    }

    @Bean
    public OpenAPI cinemaOpenAPI() {
        final String apiTitle = String.format("%s API", this.applicationName);
        return new OpenAPI()
                //.components()
                .info(new Info().title(apiTitle).version(this.apiVersion));
    }
}
