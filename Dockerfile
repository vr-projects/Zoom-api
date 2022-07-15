FROM openjdk:17
EXPOSE 8080
ADD target/spring-boot-0.0.1.jar spring-boot-0.0.1.jar
ENTRYPOINT ["java","-jar","spring-boot-0.0.1.jar"]