# Use an official Maven image as the base image
FROM maven:3.9.6-amazoncorretto-21-al2023 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Build the application using Maven
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-alpine
MAINTAINER parkingalley
#WORKDIR /app
COPY --from=build /app/target/parkingalley-analytics-1.0.0.jar .
#COPY target/parkingalley-analytics-1.0.0.jar parkingalley-analytics-1.0.0.jar
ENTRYPOINT ["java","-jar","/parkingalley-analytics-1.0.0.jar"]


#mvn clean install
#docker build --tag=parkingalley-analytics:latest .
#docker run -p8887:8080 parkingalley-analytics:latest


# Copy the built JAR file from the previous stage to the container
#COPY - from=build /app/target/my-application.jar .
