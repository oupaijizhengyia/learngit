FROM maven:latest AS appserver
ENV SOURCE_PATH="."
WORKDIR /usr/src
#COPY $SOURCE_PATH/pom.xml .
#RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY $SOURCE_PATH .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM java:8-jdk-alpine

WORKDIR /app
COPY --from=appserver /usr/src/target/tcmts2-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/app/tcmts2-0.0.1-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=prod"]

