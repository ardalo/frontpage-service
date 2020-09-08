# Ardalo Digital Platform Frontpage Service
Provides the frontpage of the public part of the Ardalo Digital Platform.

## Tech Info

* Java 11
* Gradle
* Spring Boot
* Spring MockMvc
* Spock
* Thymeleaf
* Prometheus Metrics
* Swagger UI
* Access and Application Logs in JSON format
* JaCoCo Code Coverage Report
* Docker

## Quick Start

* Start application
    ```bash
    $ ./gradlew bootRun
    ```
* Run tests
    ```bash
    $ ./gradlew clean test
    ```
* Generate Code Coverage Report. HTML Report can be found in `./build/reports/jacoco/test/html`
    ```bash
    $ ./gradlew check jacocoTestReport
    ```
* Build application JAR (and run tests)
    ```bash
    $ ./gradlew clean build
    ```
* Build Docker Image using `docker-compose`
    ```bash
    $ docker-compose build
    ```
* Start Docker Container using `docker-compose`
    ```bash
    $ docker-compose up
    ```

## API Documentation
The OpenAPI Documentation (Swagger UI) can be found at the root path of the service (e.g. `http://localhost:8081/`).
It provides an overview of all endpoints.
