package com.ardalo.digitalplatform.frontpage.apidoc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerUiWebMvcConfigurer implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/apidoc", "/apidoc/swagger-ui/index.html");
    registry.addRedirectViewController("/apidoc/", "/apidoc/swagger-ui/index.html");
  }
}
