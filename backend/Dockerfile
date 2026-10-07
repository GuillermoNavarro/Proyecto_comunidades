FROM eclipse-temurin:21-jdk-alpine AS base
WORKDIR /app/backend
COPY .mvn/ .mvn/
COPY pom.xml mvnw ./
RUN chmod +x mvnw
EXPOSE 8080

FROM base AS develop
COPY src/ ./src/
CMD ["./mvnw", "spring-boot:run"]

FROM base AS test
COPY src/ ./src/
CMD ["./mvnw", "test"]

FROM base AS build-production
RUN ./mvnw dependency:go-offline -B
COPY src/ ./src/
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine AS production
WORKDIR /app/backend
COPY --from=build-production /app/backend/target/*.jar app.jar
RUN addgroup -g 1500 -S spring && adduser -u 1500 -S spring -G spring 
# Cambiar el usuario de la carpeta uploads
# sudo chown -R 1500:1500 uploads
USER spring
ENTRYPOINT [ "java", "-jar", "app.jar" ]


