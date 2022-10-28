FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvnw clean package

FROM openjdk:17-oracle
COPY /target/dawgchat-0.0.1-SNAPSHOT.jar dawgchat-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/dawgchat-0.0.1-SNAPSHOT.jar"]