package com.ardalo.digitalplatform.frontpage.apidoc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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
    def result = mockMvc.perform(MockMvcRequestBuilders.get("/apidoc/swagger-ui/index.html")).andReturn()

    then:
    result.response.status == 200
    result.response.contentAsString.contains("<title>Swagger UI</title>")
  }

  def "should redirect GET /apidoc to /apidoc/swagger-ui/index.html"() {
    when:
    def result = mockMvc.perform(MockMvcRequestBuilders.get(requestPath)).andReturn()

    then:
    result.response.status == 302
    result.response.getHeader('Location') == "/apidoc/swagger-ui/index.html"
    result.response.contentAsString == ""

    where:
    requestPath << ["/apidoc", "/apidoc/"]
  }

  def "should provide OpenAPI v3 API doc"() {
    when:
    def result = mockMvc.perform(MockMvcRequestBuilders.get("/apidoc/v3")).andReturn()

    then:
    result.response.status == 200
    result.response.contentType == MediaType.APPLICATION_JSON.toString()
    result.response.contentAsString.contains('"openapi":"3.0.3"')
  }

  def "should provide Swagger v2 API doc"() {
    when:
    def result = mockMvc.perform(MockMvcRequestBuilders.get("/apidoc/v2")).andReturn()

    then:
    result.response.status == 200
    result.response.contentType == MediaType.APPLICATION_JSON.toString()
    result.response.contentAsString.contains('"swagger":"2.0"')
  }
}
