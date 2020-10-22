FROM maven:3.6.3 AS build  

WORKDIR /code

COPY src /code/app/src
COPY pom.xml /code/app
RUN mvn -f /code/app/pom.xml clean install

FROM openjdk:11.0.8
COPY --from=build /code/app/target/tallerSeguridadBackend-0.0.1-SNAPSHOT.jar tallerSeguridadBackend-0.0.1-SNAPSHOT.jar
EXPOSE 8080

CMD ["java", "-jar", "tallerSeguridadBackend-0.0.1-SNAPSHOT.jar"]