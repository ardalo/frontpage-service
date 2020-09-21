package com.ardalo.digitalplatform.frontpage.ardaloplatform

import okhttp3.mockwebserver.MockWebServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@SpringBootTest
class PlatformRoutesUpdaterSpec extends Specification {

  private MockWebServer mockWebServer

  def setup() {
    this.mockWebServer = new MockWebServer()
    this.mockWebServer.start()
  }

  def cleanup() {
    this.mockWebServer.shutdown()
  }

  def "should send route creation request to platform gateway"() {
    given:
    def platformRoutesUpdater = new PlatformRoutesUpdater(new ArdaloPlatformConfig(
      platformGateway: new ArdaloPlatformConfig.PlatformGateway(
        baseUrl: "http://${this.mockWebServer.hostName}:${this.mockWebServer.port}/api",
        platformRoutes: [new ArdaloPlatformConfig.PlatformRoute(
          id: "test-create-platform-routes",
          definition: '{"foo":"bar"}'
        )]
      )
    ))

    when:
    platformRoutesUpdater.updatePlatformRoutes()

    then:
    def recordedRequest = mockWebServer.takeRequest(200, TimeUnit.MILLISECONDS)
    recordedRequest.method == HttpMethod.POST.toString()
    recordedRequest.path == "/api/routes/v1/test-create-platform-routes"
    recordedRequest.getHeader(HttpHeaders.CONTENT_TYPE) == MediaType.APPLICATION_JSON.toString()
    recordedRequest.body.readUtf8() == '{"foo":"bar"}'
  }
}
