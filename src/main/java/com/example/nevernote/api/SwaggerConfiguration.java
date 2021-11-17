package com.example.nevernote.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
  public static final String TITLE = "Nevernote API";
  public static final String DESCRIPTION = "Nevernote API";
  public static final String VERSION = "1.0";

  @Bean
  public OpenAPI configureControllerPackageAndConvertors() {
    return new OpenAPI().info(apiInfo());
  }

  public Info apiInfo() {
    return new Info().title(TITLE).description(DESCRIPTION).version(VERSION);
  }
}
