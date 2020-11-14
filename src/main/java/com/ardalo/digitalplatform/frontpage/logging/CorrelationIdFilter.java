package com.ardalo.digitalplatform.frontpage.logging;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class CorrelationIdFilter implements Filter {

  private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
  private static final String CORRELATION_ID_MDC_FIELD_NAME = "correlationId";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    try {
      String correlationId = getCorrelationId(httpRequest);
      httpResponse.addHeader(CORRELATION_ID_HEADER, correlationId);
      MDC.put(CORRELATION_ID_MDC_FIELD_NAME, correlationId);
      chain.doFilter(request, response);
    } finally {
      MDC.remove(CORRELATION_ID_MDC_FIELD_NAME);
    }
  }

  private String getCorrelationId(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(CORRELATION_ID_HEADER))
      .map(correlationId -> correlationId.replaceAll("[^a-zA-Z0-9-_.]", ""))
      .orElse(UUID.randomUUID().toString().replace("-", ""));
  }
}
