# Estágio 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom.xml e baixar dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código fonte e fazer build
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expor a porta do backend
EXPOSE 8080

# Variáveis de ambiente (serão sobrescritas pelo docker-compose)
ENV WEATHER_API_KEY=""
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]