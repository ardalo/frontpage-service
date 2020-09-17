package com.ardalo.digitalplatform.frontpage.ardaloplatform;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ardalo-platform")
public class ArdaloPlatformConfig {

  private PlatformGateway platformGateway;

  public PlatformGateway getPlatformGateway() {
    return platformGateway;
  }

  public void setPlatformGateway(PlatformGateway platformGateway) {
    this.platformGateway = platformGateway;
  }

  public static class PlatformGateway {

    private String baseUrl;
    private List<PlatformRoute> platformRoutes;

    public String getBaseUrl() {
      return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
    }

    public List<PlatformRoute> getPlatformRoutes() {
      return platformRoutes;
    }

    public void setPlatformRoutes(List<PlatformRoute> platformRoutes) {
      this.platformRoutes = platformRoutes;
    }
  }

  public static class PlatformRoute {

    private String id;
    private String definition;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getDefinition() {
      return definition;
    }

    public void setDefinition(String definition) {
      this.definition = definition;
    }
  }
}
