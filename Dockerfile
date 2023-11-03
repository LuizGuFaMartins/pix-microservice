FROM maven:3.6.3-openjdk-17 as build
RUN mkdir -p /home/src/app
WORKDIR /home/src/app
ADD . /home/src/app
RUN mvn package

FROM eclipse-temurin:17
RUN mkdir -p /home/src/app
WORKDIR /home/src/app
COPY --from=build /home/src/app/target/pix-microservice-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]

COPY producao-503840-prod-cert.p12 .

EXPOSE 8080