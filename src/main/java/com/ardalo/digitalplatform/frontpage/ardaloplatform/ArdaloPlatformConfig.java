package com.ardalo.digitalplatform.frontpage.ardaloplatform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ArdaloPlatformConfig {

  @Value("${ardalo-platform.platform-gateway.base-url}")
  private String platformGatewayBaseUrl;

  public String getPlatformGatewayBaseUrl() {
    return platformGatewayBaseUrl;
  }

  public void setPlatformGatewayBaseUrl(String platformGatewayBaseUrl) {
    this.platformGatewayBaseUrl = platformGatewayBaseUrl;
  }
}
