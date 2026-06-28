FROM eclipse-temurin:17
LABEL authors="Aloui Omar"

EXPOSE 8083
ADD target/job-0.0.1-SNAPSHOT.jar job.jar

ENTRYPOINT ["java", "-jar", "job.jar"]