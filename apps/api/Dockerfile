FROM maven:3.9.12-eclipse-temurin-25 AS deps
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -q -B

FROM deps AS builder
COPY src ./src
RUN mvn clean package -DskipTests -o -q -B

FROM eclipse-temurin:25-jre AS runner

# curl is only used by the HEALTHCHECK below
RUN apt-get update \
  && apt-get install -y --no-install-recommends curl \
  && rm -rf /var/lib/apt/lists/* \
  && groupadd -r bookstudio && useradd -r -g bookstudio bookstudio

WORKDIR /app
COPY --from=builder --chown=bookstudio:bookstudio /app/target/bookstudio-*.jar app.jar

USER bookstudio

EXPOSE 8080

HEALTHCHECK --interval=15s --timeout=5s --start-period=40s --retries=5 \
  CMD curl -sf http://localhost:8080/actuator/health/readiness || exit 1

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
