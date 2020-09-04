package com.ardalo.digitalplatform.frontpage

import org.junit.Rule
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.OutputCaptureRule
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = "spring.profiles.active=prod"
)
class FrontpageServiceApplicationIT extends Specification {

  @Rule
  OutputCaptureRule outputCapture = new OutputCaptureRule()

  @Autowired
  ApplicationContext applicationContext

  def "should load context"() {
    expect:
    applicationContext != null
  }

  def "should write application logs in JSON format for profile=prod"() {
    when:
    LoggerFactory.getLogger("test-logger").warn("test message")
    def logMessage = outputCapture.toString()

    then:
    logMessage.startsWith('{')
    logMessage.contains('"msg":"test message"')
    logMessage.contains('"logger":"test-logger"')
    logMessage.contains('"level":"WARN"')
    logMessage.endsWith('}\n')
  }
}
