package com.ardalo.digitalplatform.frontpage.swagger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerUiIT extends Specification {

  @Autowired
  MockMvc mockMvc

  def "should provide Swagger UI"() {
    when:
    def result = mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui/index.html")).andReturn()

    then:
    result.response.status == 200
    result.response.contentAsString.contains("<title>Swagger UI</title>")
  }

  def "should redirect GET / to /swagger-ui/index.html"() {
    when:
    def result = mockMvc.perform(MockMvcRequestBuilders.get("/")).andReturn()

    then:
    result.response.status == 302
    result.response.getHeader('Location') == "/swagger-ui/index.html"
    result.response.contentAsString == ""
  }
}
