FROM openjdk:17-jdk-alpine
EXPOSE 9000
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]