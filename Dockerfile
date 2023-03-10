FROM openjdk:17-jdk-alpine
COPY build/libs/notif-service-0.0.1-SNAPSHOT.jar notif-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/notif-service-0.0.1-SNAPSHOT.jar"]