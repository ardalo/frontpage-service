# Ardalo Digital Platform Frontpage Service
![Build Status](https://github.com/ardalo/frontpage-service/workflows/Build/badge.svg)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=ardalo_frontpage-service&metric=coverage)](https://sonarcloud.io/dashboard?id=ardalo_frontpage-service)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=ardalo_frontpage-service&metric=ncloc)](https://sonarcloud.io/dashboard?id=ardalo_frontpage-service)

Provides the frontpage of the public part of the Ardalo Digital Platform.

## Tech Info
__Java Spring Boot Service__
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
* Static Code Analysis via SonarCloud
* Docker
* CI/CD: GitHub Actions

## Quick Start
* Run via Docker using `docker-compose` and find API docs at `http://localhost:8081/`
  ```console
  $ docker-compose build && docker-compose up
  ```
* Start application and find API docs at `http://localhost:8081/`
  ```console
  $ ./gradlew bootRun
  ```
* Run tests
  ```console
  $ ./gradlew test
  ```
* Generate Code Coverage Report. HTML Report can be found in `./build/reports/jacoco/test/html`
  ```console
  $ ./gradlew check jacocoTestReport
  ```
* Check for outdated dependencies via [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin)
  ```console
  $ ./gradlew dependencyUpdates -Drevision=release
  ```

## API Documentation
Swagger UI is accessible via `/apidoc` (e.g. http://localhost:8081/apidoc).
It provides an overview of all endpoints.
