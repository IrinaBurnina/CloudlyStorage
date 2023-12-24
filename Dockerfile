FROM openjdk:17-jdk-alpine

EXPOSE 8081

ADD target/CloudlyStorage-0.0.1-SNAPSHOT.jar myapp.jar

CMD ["java", "-jar", "/myapp.jar"]