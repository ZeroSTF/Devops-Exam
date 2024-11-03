FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/tp-foyer-5.0.0.jar /app/tp-foyer-5.0.0.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app/tp-foyer-5.0.0.jar"]