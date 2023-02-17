FROM --platform=linux/amd64 openjdk:17-alpine
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/ms-sysfinan-cadastro.jar
WORKDIR /app
ENTRYPOINT java -jar ms-sysfinan-cadastro.jar