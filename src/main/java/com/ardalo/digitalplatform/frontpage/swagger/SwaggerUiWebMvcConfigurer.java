package com.ardalo.digitalplatform.frontpage.swagger;

import java.util.stream.Stream;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerUiWebMvcConfigurer implements WebMvcConfigurer {

  private static final String SWAGGER_UI_NORMALIZED_PATH = "/swagger-ui/index.html";

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    Stream.of("/", "/swagger-ui", "/swagger-ui/")
      .forEach(requestPath -> registry.addRedirectViewController(requestPath, SWAGGER_UI_NORMALIZED_PATH));
  }
}
