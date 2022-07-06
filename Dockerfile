FROM openjdk:11-jre-slim
MAINTAINER test.com
VOLUME /tmp
EXPOSE 8080
ADD target/challenge-1.0.0-SNAPSHOT.jar challenge.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/challenge.jar"]
