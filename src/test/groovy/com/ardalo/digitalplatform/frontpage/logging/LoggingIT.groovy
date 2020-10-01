package com.ardalo.digitalplatform.frontpage.logging

import org.junit.Rule
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.OutputCaptureRule
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

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
    def logMessage = getAccessLogFor("/alive?foo=bar", new RestTemplate())

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
    logMessage.matches(/.+"correlationId":"[a-z0-9]{32}".+/)
    logMessage.contains('"remoteIp":')
    !logMessage.contains('"user":')
    logMessage.endsWith('}')
  }

  def "should write correlation id from X-Correlation-ID request header to access logs"() {
    when:
    def logMessage = getAccessLogFor("/alive", new RestTemplateBuilder().defaultHeader("X-Correlation-ID", "test-correlation-id-header").build())

    then:
    logMessage.contains('"correlationId":"test-correlation-id-header"')
  }

  private getAccessLogFor(String requestPath, RestTemplate restTemplate) {
    restTemplate.getForEntity("http://localhost:" + port + requestPath, String.class)
    return getLastLineFromOutputCapture()
  }

  private getLastLineFromOutputCapture() {
    def logMessage = outputCapture.toString().trim()
    return logMessage.substring(logMessage.contains("\n") ? logMessage.lastIndexOf("\n") : 0).trim()
  }
}
