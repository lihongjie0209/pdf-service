FROM maven:3-eclipse-temurin-21-jammy as build
WORKDIR /code
COPY pom.xml .
COPY settings.xml .

RUN mvn verify --fail-never -P build --settings settings.xml && mvn dependency:resolve-plugins  -P build --settings settings.xml

COPY src ./src
RUN mvn clean install -DskipTests -P build --settings settings.xml && \
    java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

FROM mcr.microsoft.com/playwright/java:v1.51.0-noble

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY --from=build /code/target/extracted/dependencies/ ./
COPY --from=build /code/target/extracted/spring-boot-loader/ ./
COPY --from=build /code/target/extracted/snapshot-dependencies/ ./
COPY --from=build /code/target/extracted/application/ ./

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} org.springframework.boot.loader.launch.JarLauncher"]