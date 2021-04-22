FROM gradle:jdk16 as builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle -q bootJar

FROM adoptopenjdk/openjdk16:alpine-jre
RUN addgroup -S ardalo && adduser -S ardalo -G ardalo
USER ardalo:ardalo
COPY --from=builder /app/build/libs /app

ENTRYPOINT ["java", "-jar", "/app/frontpage-service.jar"]
