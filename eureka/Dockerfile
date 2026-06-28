FROM eclipse-temurin:17
LABEL authors="Aloui Omar"

EXPOSE 8761
ADD target/eureka-0.0.1-SNAPSHOT.jar eureka.jar

ENTRYPOINT ["java", "-jar", "eureka.jar"]