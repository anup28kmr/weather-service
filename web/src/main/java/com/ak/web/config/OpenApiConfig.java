package com.ak.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI weatherServiceOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Weather Service API")
            .description("API for retrieving weather forecasts from various locations")
            .version("1.0.0")
            .contact(new Contact()
                .name("Weather Service Team")
                .email("support@weatherservice.com")))
        .addServersItem(new Server().url("/").description("Default Server URL"));
  }
}