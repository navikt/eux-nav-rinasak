FROM gcr.io/distroless/java21
COPY eux-nav-rinasak-webapp/target/eux-nav-rinasak.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
