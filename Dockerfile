FROM openjdk:17  # 🔹 Corregir "ROM" por "FROM"
WORKDIR /app
COPY target/backend-springboot-1.0.0.jar app.jar
EXPOSE 8081  # 🔹 Cambiar a 8081 para coincidir con `application.yml`
CMD ["java", "-jar", "app.jar"]
