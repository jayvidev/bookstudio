FROM maven:3.9.12-eclipse-temurin-25 AS deps
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -q -B

FROM deps AS builder
COPY src ./src
RUN mvn clean package -DskipTests -o -q -B

FROM eclipse-temurin:25-jre AS runner

RUN groupadd -r bookstudio && useradd -r -g bookstudio bookstudio

WORKDIR /app
COPY --from=builder --chown=bookstudio:bookstudio /app/target/bookstudio-*.jar app.jar

USER bookstudio

EXPOSE 8080

ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-Djava.security.egd=file:/dev./urandom", \
  "-jar", "app.jar"]
