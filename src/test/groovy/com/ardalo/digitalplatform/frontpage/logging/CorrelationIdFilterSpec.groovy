package com.ardalo.digitalplatform.frontpage.logging

import com.ardalo.digitalplatform.frontpage.infrastructure.logging.CorrelationIdFilter
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CorrelationIdFilterSpec extends Specification {

  def "should sanitize provided correlation id"() {
    given:
    def request = Mock(HttpServletRequest)
    def response = Mock(HttpServletResponse)
    def filterChain = Mock(FilterChain)
    request.getHeader("X-Correlation-ID") >> "!+test-correlation,-,id-§§foo_bar//"

    when:
    new CorrelationIdFilter().doFilter(request, response, filterChain)

    then:
    1* response.addHeader("X-Correlation-ID", "test-correlation-id-foo_bar")
    1* filterChain.doFilter(request, response)
  }

  def "should generate correlation id if none is provided"() {
    given:
    def request = Mock(HttpServletRequest)
    def response = Mock(HttpServletResponse)
    def filterChain = Mock(FilterChain)
    request.getHeader("X-Correlation-ID") >> null

    when:
    new CorrelationIdFilter().doFilter(request, response, filterChain)

    then:
    1* response.addHeader("X-Correlation-ID", { it.matches(/^[a-z0-9]+$/) })
    1* filterChain.doFilter(request, response)
  }
}
