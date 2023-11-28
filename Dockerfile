FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY controller/lyfe-api/build/libs/*.jar lyfe-server.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/lyfe-server.jar"]
