## Build stage
#FROM maven:3.8.6-openjdk-17 AS build
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:go-offline
#COPY src /app/src
#RUN mvn package -DskipTests
#
## Run stage
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY --from=build /app/target/job-scheduler-*.jar app.jar
#
## Create directory for job execution temp files
#RUN mkdir -p /tmp/job-executions && \
#    chmod 777 /tmp/job-executions
#
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]


# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/job-scheduler-*.jar app.jar

# Create directory for job execution temp files
RUN mkdir -p /tmp/job-executions && \
    chmod 777 /tmp/job-executions

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
