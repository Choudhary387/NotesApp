# Step 1: Use an official Java runtime as a parent image
FROM openjdk:21-jdk-apline

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy your project's jar file into the container
# Replace 'target/myapp.jar' with the path to your built jar
COPY target/*.jar app.jar

# Step 4: Expose the port your app runs on
EXPOSE 9090

# Step 5: Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]