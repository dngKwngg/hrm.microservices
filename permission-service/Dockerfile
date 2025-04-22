# Use a lightweight Java image
FROM openjdk:17-jdk-slim

# Create a directory in the container
VOLUME /tmp

# Pass the built JAR from the target folder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]
