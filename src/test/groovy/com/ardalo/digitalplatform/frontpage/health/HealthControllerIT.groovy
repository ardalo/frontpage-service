package com.ardalo.digitalplatform.frontpage.health

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class HealthControllerIT extends Specification {

  @Autowired
  MockMvc mockMvc

  def "should return 200 OK for alive endpoint"() {
    when:
    def result = mockMvc.perform(MockMvcRequestBuilders.get("/alive")).andReturn()

    then:
    result.response.status == 200
    result.response.contentAsString == ""
  }

  def "should return 200 OK for ready endpoint"() {
    when:
    def result = mockMvc.perform(MockMvcRequestBuilders.get("/ready")).andReturn()

    then:
    result.response.status == 200
    result.response.contentAsString == ""
  }
}
