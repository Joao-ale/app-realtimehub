# Multi-stage build for RealtimeHub API

# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build

# Install build dependencies
RUN apk add --no-cache bash git

# Copy gradle files
COPY gradle ./gradle
COPY gradlew .
COPY build.gradle.kts gradle.properties settings.gradle.kts ./

# Copy source code
COPY src ./src

# Build application
RUN chmod +x ./gradlew && ./gradlew build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Install runtime dependencies
RUN apk add --no-cache curl dumb-init

# Create non-root user
RUN addgroup -g 1000 realtimehub && adduser -D -u 1000 -G realtimehub realtimehub

# Copy built artifact from builder
COPY --from=builder --chown=realtimehub:realtimehub /build/build/libs/*.jar app.jar

# Switch to non-root user
USER realtimehub

# Health check
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8080/api/health || exit 1

# Use dumb-init to handle signals properly
ENTRYPOINT ["dumb-init", "--"]
CMD ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-Djava.net.preferIPv4Stack=true", "-jar", "app.jar"]
