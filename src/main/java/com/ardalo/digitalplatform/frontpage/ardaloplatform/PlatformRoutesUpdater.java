package com.ardalo.digitalplatform.frontpage.ardaloplatform;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
public class PlatformRoutesUpdater {

  private static final Logger logger = LoggerFactory.getLogger(PlatformRoutesUpdater.class);

  private final WebClient webClient;

  @Autowired
  public PlatformRoutesUpdater(ArdaloPlatformConfig ardaloPlatformConfig) {
    this.webClient = WebClient.builder()
      .baseUrl(ardaloPlatformConfig.getPlatformGatewayBaseUrl())
      .build();
  }

  @EventListener(ApplicationReadyEvent.class)
  public void updatePlatformRoutes() {
    this.webClient
      .post()
      .uri("/routes/v1/frontpage")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue("{\"uri\":\"http://frontpage-service:8081/api/pages/frontpage\",\"predicates\":[{\"name\":\"Path\",\"args\":{\"arg0\":\"/\"}}]}")
      .exchange()
      .doOnSuccess(response -> logger.info("Updated routes at Platform Gateway"))
      .doOnError(e -> logger.error("Unable to update routes at Platform Gateway: {}", e.getMessage()))
      .retryWhen(Retry.fixedDelay(Integer.MAX_VALUE, Duration.ofMinutes(1)))
      .block();
  }
}
