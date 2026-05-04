# Etapa 1: Construcción (Builder)
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copiamos wrapper y pom
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# CLAVE: dar permisos
RUN chmod +x mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline

# Copiar código
COPY src ./src

# Build
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]