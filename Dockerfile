# Use an official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set environment variables (optional if using a config file)
ENV DB_URL=jdbc:mysql://mysql_container:3306/todoapp
ENV DB_USER=root
ENV DB_PASSWORD=

# Set a working directory (optional)
WORKDIR /app

# Copy the JAR file from the target folder to the container
COPY target/todo-app-1.0-SNAPSHOT.jar /app/app.jar

# Copy the configuration file if needed
COPY config-docker.yml /app/config.yml

# Expose the application's port (match your Dropwizard config port)
EXPOSE 8081

# Run the application with required arguments
ENTRYPOINT ["java", "-jar", "/app/app.jar", "server", "/app/config.yml"]
