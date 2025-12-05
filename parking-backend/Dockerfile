#construimos
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

#copiamos el pom a workdir
COPY pom.xml .

#guardamos cosas en cache
RUN mvn dependency:go-offline

#copiamos el code fuente
COPY src ./src

#compilamos
RUN mvn clean package -DskipTests

#ejecutamos
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]