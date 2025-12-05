FROM cimg/openjdk:17.0
WORKDIR /app
COPY target/config_server-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]