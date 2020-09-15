package com.ardalo.digitalplatform.frontpage.ardaloplatform

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class PlatformRoutesUpdaterSpec extends Specification {

  @Autowired
  MockMvc mockMvc

  def "should expose prometheus metrics endpoint"() {
    when:
    def result = mockMvc.perform(MockMvcRequestBuilders.get("/prometheus-metrics")).andReturn()

    then:
    result.response.status == 200
    MediaType.valueOf(result.response.contentType).isCompatibleWith(MediaType.valueOf("text/plain; charset=utf8"))
    result.response.contentAsString.contains("jvm_memory_max_bytes")
  }
}
