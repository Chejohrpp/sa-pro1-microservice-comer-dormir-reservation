# Etapa 1: Usar Maven para compilar el proyecto con OpenJDK 21
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

  # Copia los archivos de configuración de Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

  # Copia el código fuente y compila el proyecto
COPY src ./src
RUN mvn package -DskipTests

  # Etapa 2: Usar una imagen ligera de OpenJDK 21 para ejecutar la aplicación
FROM openjdk:21

WORKDIR /app

  # Copia el archivo JAR generado por Maven en la etapa anterior
COPY --from=build /app/target/reservation-microservices-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
