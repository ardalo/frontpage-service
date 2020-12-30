package com.ardalo.digitalplatform.frontpage.logging

import org.junit.Rule
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.OutputCaptureRule
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import java.util.stream.Collectors

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoggingIT extends Specification {

  @LocalServerPort
  int port

  @Rule
  OutputCaptureRule outputCapture = new OutputCaptureRule()

  def "should write application logs in JSON format"() {
    when:
    LoggerFactory.getLogger("test-logger").warn("test message")
    def logMessage = getLastLineFromOutputCapture()

    then:
    logMessage.startsWith('{')
    logMessage.contains('"@timestamp":"')
    logMessage.contains('"type":"application"')
    logMessage.contains('"msg":"test message"')
    logMessage.contains('"logger":"test-logger"')
    logMessage.contains('"level":"WARN"')
    logMessage.contains('"class":"com.ardalo.digitalplatform.frontpage.logging.LoggingIT"')
    logMessage.contains('"method":"')
    logMessage.contains('"file":"LoggingIT.groovy"')
    logMessage.contains('"line":')
    logMessage.endsWith('}')
  }

  def "should write access logs in JSON format"() {
    when:
    new RestTemplate().getForEntity("http://localhost:" + port + "/alive?foo=bar", String.class)
    def logMessage = getLastLineFromOutputCapture()

    then:
    logMessage.startsWith('{')
    logMessage.contains('"@timestamp":')
    logMessage.contains('"type":"access"')
    logMessage.contains('"protocol":"HTTP/1.1"')
    logMessage.contains('"method":"GET"')
    logMessage.contains('"path":"/alive"')
    logMessage.contains('"query":"?foo=bar"')
    logMessage.contains('"status":200')
    logMessage.contains('"duration":')
    !logMessage.contains('"bytesSent":')
    logMessage.contains('"userAgent":"Java/')
    logMessage.matches(/.+"requestId":"[a-z0-9]{32}".+/)
    logMessage.contains('"remoteIp":')
    !logMessage.contains('"user":')
    logMessage.endsWith('}')
  }

  def "should write request id from X-Request-ID request header to access logs"() {
    when:
    new RestTemplateBuilder()
      .defaultHeader("X-Request-ID", "test-request-id-header")
      .build()
      .getForEntity("http://localhost:" + port + "/alive", String.class)
    def logMessage = getLastLineFromOutputCapture()

    then:
    logMessage.contains('"requestId":"test-request-id-header"')
  }

  private getLastLineFromOutputCapture() {
    def logMessage = outputCapture.toString().trim()
    return logMessage.lines().collect(Collectors.toList()).last().trim()
  }
}
