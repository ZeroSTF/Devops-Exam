FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app/app.jar"]


