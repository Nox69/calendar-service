FROM adoptopenjdk:11-jre-hotspot
COPY build/libs/calendar-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
