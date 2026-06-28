FROM eclipse-temurin:17
LABEL authors="Aloui Omar"

EXPOSE 8880
ADD target/gateway-0.0.1-SNAPSHOT.jar gateway.jar

ENTRYPOINT ["java", "-jar", "gateway.jar"]