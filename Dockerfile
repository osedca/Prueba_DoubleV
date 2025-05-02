FROM openjdk:17
WORKDIR /app
COPY target/backend-springboot-1.0.0.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
