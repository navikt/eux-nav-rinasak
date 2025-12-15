FROM gcr.io/distroless/java25
COPY eux-nav-rinasak-webapp/target/eux-nav-rinasak.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
