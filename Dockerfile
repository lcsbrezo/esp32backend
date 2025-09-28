# Etapa 1: construir la aplicación
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias (cacheable)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto del código y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: ejecutar la aplicación
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Puerto que Render asignará
EXPOSE 8095

# Comando para ejecutar Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
