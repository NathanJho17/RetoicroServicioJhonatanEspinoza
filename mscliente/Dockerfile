# Etapa 1: Build con Gradle
FROM gradle:8.10.0-jdk17 AS build

# Crear directorio de trabajo
WORKDIR /app

# Copiar el wrapper y configuración
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Compilar la app sin ejecutar tests
RUN gradle clean bootJar --no-daemon

# Etapa 2: Imagen liviana con JDK para ejecución
FROM eclipse-temurin:17-jdk-alpine

# Directorio donde se ejecutará la app
WORKDIR /app

# Copiar el .jar generado desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto de Spring Boot (ajusta si usas otro)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
