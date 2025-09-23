FROM openjdk:24-ea-21-bullseye as builder

WORKDIR /app/managament-equipments

COPY ./pom.xml .
COPY  .mvn ./.mvn
COPY mvnw .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY /src ./src

RUN ./mvnw clean package -DskipTests


FROM openjdk:24-ea-21-slim-bullseye

WORKDIR /app

COPY --from=builder /app/managament-equipments/target/managament-equipments-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "./managament-equipments-0.0.1-SNAPSHOT.jar"]