FROM openjdk:17-jdk-slim AS build

COPY . /usr/src/tasks

RUN apt update && apt install -y maven
RUN mvn -f /usr/src/tasks/pom.xml clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /usr/src/tasks/target/tasks-0.0.1-SNAPSHOT.jar /usr/tasks/tasks-0.0.1-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/tasks/tasks-0.0.1-SNAPSHOT.jar"]
