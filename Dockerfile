FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app 
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11

RUN mkdir /opt/results

RUN mkdir /app
WORKDIR /app
COPY --from=build /home/app/target/minbenchmark-3.jar /app/minbenchmark-3.jar
EXPOSE 9081

ENTRYPOINT ["java", "-jar", "/app/minbenchmark-3.jar"]