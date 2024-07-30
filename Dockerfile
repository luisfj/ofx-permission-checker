FROM openjdk:21-jdk-slim
EXPOSE 8050
ADD target/ofx-permission-checker.jar ofx-permission-checker.jar
ENTRYPOINT [ "java", "-jar", "/ofx-permission-checker.jar" ]