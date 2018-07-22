FROM openjdk:8-jre

RUN mkdir -p /usr/src/app

COPY ./target/*.jar /usr/src/app/app.jar


CMD java -jar /usr/src/app/app.jar