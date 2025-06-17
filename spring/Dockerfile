FROM maven:3.9.9-amazoncorretto-21-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests && rm -rf ~/.m2

FROM sapmachine:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8100

ENTRYPOINT ["java", "-jar", "app.jar"]

