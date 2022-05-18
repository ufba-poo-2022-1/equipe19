FROM maven:3.6.0-jdk-11-slim AS build

WORKDIR /app

COPY src /src

COPY pom.xml /app

RUN mvn clean package

FROM openjdk:11-jre-slim

COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar

EXPOSE 5000

ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]