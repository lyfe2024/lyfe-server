FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY server/lyfe-api/build/libs/*.jar lyfe-server.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/lyfe-server.jar"]
