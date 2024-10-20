FROM openjdk:17-alpine
COPY ./build/libs/batch.jar /minionz/batch.jar

# ARG 선언
ARG DB_PASSWORD
ARG DB_URL
ARG DB_USERNAME
ARG KAFKA_SERVER

# ENV 설정
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV KAFKA_SERVER=${KAFKA_SERVER}

CMD ["java", "-jar", "/minionz/batch.jar"]
