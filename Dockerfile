FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-25
ENV TZ="Europe/Oslo"
ENV JDK_JAVA_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
COPY eux-nav-rinasak-webapp/target/eux-nav-rinasak.jar /app.jar
CMD ["-jar", "/app.jar"]
