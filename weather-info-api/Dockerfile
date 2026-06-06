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

# Expor a porta 80 (padrão HTTP para o SnapDeploy)
EXPOSE 80

# Variáveis de ambiente
ENV WEATHER_API_KEY=wai_3abc02.e6170c5e3b2e93879e3c9d031963dcd0b40187de44a1a170
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=80

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "-DSERVER_PORT=80", "app.jar"]