FROM amazoncorretto:21.0.2-alpine

WORKDIR /api

COPY ./build/libs/api-*.jar .

RUN rm -f api-*-plain.jar

RUN mv api-*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
