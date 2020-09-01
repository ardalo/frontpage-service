package com.ardalo.digitalplatform.frontpage.swagger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerUiIT extends Specification {

  @Autowired
  MockMvc mockMvc

  @Unroll
  def "should redirect GET #requestUrl to /swagger-ui/index.html"(String requestUrl) {
    when:
    def result = mockMvc.perform(MockMvcRequestBuilders.get(requestUrl)).andReturn()

    then:
    result.response.status == 302
    result.response.getHeader('Location') == "/swagger-ui/index.html"
    result.response.contentAsString == ""

    where:
    requestUrl << [ "/", "/swagger-ui", "/swagger-ui/" ]
  }
}
