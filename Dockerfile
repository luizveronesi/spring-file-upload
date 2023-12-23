FROM adoptopenjdk/openjdk11
VOLUME /tmp
ADD target/upload.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]