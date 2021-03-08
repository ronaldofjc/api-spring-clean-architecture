FROM openjdk:11.0-jre-slim

VOLUME /tmp
ARG JAR_FILE=./target/sample-project-spring-clean-arch*.jar
ARG PROFILE=default
ENV PROFILE=${PROFILE}
ENV JAR_FILE=${JAR_FILE}

COPY $JAR_FILE /opt/app.jar

ENTRYPOINT exec java -jar -Dspring.profiles.active=$PROFILE /opt/app.jar
