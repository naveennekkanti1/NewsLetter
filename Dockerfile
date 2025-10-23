# Use official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/NewsLetter-1.0-SNAPSHOT.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
