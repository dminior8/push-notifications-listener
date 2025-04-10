# Etap budowania
FROM openjdk:21-jdk-slim AS build

WORKDIR /app

# Kopiuj wszystkie wymagane pliki do Maven Wrapper
COPY .mvn/wrapper /app/.mvn/wrapper
COPY mvnw /app/mvnw
COPY pom.xml /app/pom.xml

# Nadanie uprawnień i pobranie zależności
RUN chmod +x mvnw
#RUN ./mvnw dependency:go-offline

# Skopiuj resztę projektu
COPY . .

# Buduj aplikację
#RUN ./mvnw clean package -DskipTests

# Etap uruchamiania
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/push-notifications-listener-0.0.1-SNAPSHOT.jar ./push-notifications-listener-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "push-notifications-listener-0.0.1-SNAPSHOT.jar"]