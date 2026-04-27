# Stage 1: Build stage menggunakan Maven
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /build

# Copy pom.xml dan source code
COPY pom.xml .
COPY src ./src

# Install Maven (karena kita tidak copy mvnw)
RUN apk add --no-cache maven

# Build aplikasi
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy JAR dari builder stage
COPY --from=builder /build/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/ || exit 1

# Run aplikasi
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
