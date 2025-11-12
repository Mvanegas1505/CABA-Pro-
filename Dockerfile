# Multi-stage Dockerfile for building and running the Spring Boot app
# Builder stage: builds the fat jar with Maven
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /build
# Copy maven files
COPY pom.xml .
COPY src ./src
# Build executable jar (skip tests for speed; remove -DskipTests if you want tests run)
RUN mvn -B -DskipTests clean package

# Runtime stage: lightweight JRE image
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy jar from builder stage. Use a predictable name.
COPY --from=builder /build/target/*.jar app.jar

# Create data directory for H2 file-based DB
RUN mkdir -p /app/data
VOLUME ["/app/data"]

# Expose the port the app listens on
EXPOSE 8080

# Use the dev profile by default so it uses jdbc:h2:file:./data/testdb
ENV SPRING_PROFILES_ACTIVE=dev

ENTRYPOINT ["java","-jar","/app/app.jar"]