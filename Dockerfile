# Stage 1: Build (Genera el archivo .war)
FROM gradle:8.5-jdk21 AS build 
WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2: Run (Ejecuta el archivo generado)
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/build/libs/discografia-1.war app.war 
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]
