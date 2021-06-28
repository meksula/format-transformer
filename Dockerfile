FROM openjdk:11 as build

ARG APP_NAME=transformer
ARG COMPILATION_DIR=/usr/src/${APP_NAME}

RUN apt-get update \
 && apt-get install -y procps git net-tools curl maven vim \
 && mkdir ${COMPILATION_DIR}

WORKDIR ${COMPILATION_DIR}
COPY . ${COMPILATION_DIR}

RUN mvn install -DskipTests


FROM debian:10-slim

ARG APP_NAME=transformer
ARG COMPILATION_DIR=/usr/src/${APP_NAME}
ARG EXECUTABLE_ARTIFACT=${COMPILATION_DIR}/target/format-transformer-0.0.1-SNAPSHOT.jar
ARG ARTIFACT_TARGET=/opt/${APP_NAME}

RUN mkdir ${ARTIFACT_TARGET} \
 && apt-get update \
 && apt-get install -y net-tools curl vim \
 && mkdir -p /usr/share/man/man1 \
 && apt-get install -y default-jre

WORKDIR ${ARTIFACT_TARGET}
COPY --from=0 ${EXECUTABLE_ARTIFACT} .

EXPOSE 8080

CMD ["java", "-jar", "/opt/transformer/format-transformer-0.0.1-SNAPSHOT.jar"]