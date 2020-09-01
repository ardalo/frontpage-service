# Ardalo Digital Platform Frontpage Service
Provides the frontpage of the public part of the Ardalo Digital Platform.

## Tech Stack

* Java 11
* Gradle
* Spring Boot
* Spring MockMvc
* Spock
* Thymeleaf
* Prometheus Metrics
* Swagger UI

## Quick Start

* Start application
    ```bash
    ./gradlew bootRun
    ```
* Run tests
    ```bash
    ./gradlew clean test
    ```
* Generate Code Coverage Report. HTML Report can be found in `./build/reports/jacoco/test/html`
    ```bash
    ./gradlew check jacocoTestReport
    ```
* Build application JAR (and run tests)
    ```bash
    ./gradlew clean build
    ```
