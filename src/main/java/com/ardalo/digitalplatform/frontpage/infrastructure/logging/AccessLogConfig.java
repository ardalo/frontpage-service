package com.ardalo.digitalplatform.frontpage.infrastructure.logging;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @link https://stackoverflow.com/a/63187616
 */
@Configuration
public class AccessLogConfig {

  @Bean
  public TomcatServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    LogbackValve logbackValve = new LogbackValve();
    logbackValve.setFilename(LogbackValve.DEFAULT_FILENAME);
    tomcat.addContextValves(logbackValve);
    return tomcat;
  }
}
