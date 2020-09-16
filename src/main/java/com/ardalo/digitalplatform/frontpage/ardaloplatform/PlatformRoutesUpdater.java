package com.ardalo.digitalplatform.frontpage.ardaloplatform;

import java.time.Duration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Component
public class PlatformRoutesUpdater {

  private static final Logger logger = LoggerFactory.getLogger(PlatformRoutesUpdater.class);

  private final List<ArdaloPlatformConfig.PlatformRoute> platformRoutes;
  private final WebClient webClient;

  @Autowired
  public PlatformRoutesUpdater(ArdaloPlatformConfig ardaloPlatformConfig) {
    this.platformRoutes = ardaloPlatformConfig.getPlatformGateway().getPlatformRoutes();
    this.webClient = WebClient.builder()
      .baseUrl(ardaloPlatformConfig.getPlatformGateway().getBaseUrl())
      .build();
  }

  @EventListener(ApplicationReadyEvent.class)
  public void updatePlatformRoutes() {
    this.platformRoutes
      .parallelStream()
      .forEach(platformRoute -> this.webClient
        .post()
        .uri("/routes/v1/{routeId}}", platformRoute.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(platformRoute.getDefinition())
        .exchange()
        .doOnNext(response -> {
          if (!HttpStatus.OK.equals(response.statusCode())) {
            throw new RuntimeException("Request failed with status code " + response.rawStatusCode());
          }
        })
        .doOnSuccess(response -> logger.info("Updated route at Platform Gateway (routeId={})", platformRoute.getId()))
        .doOnError(e -> logger.error("Unable to update route at Platform Gateway (routeId={}): {}", platformRoute.getId(), e.getMessage()))
        .retryWhen(Retry.fixedDelay(Integer.MAX_VALUE, Duration.ofMinutes(1)))
        .subscribe()
      );
  }
}
