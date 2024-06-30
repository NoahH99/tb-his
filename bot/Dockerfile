FROM amazoncorretto:21.0.2-alpine

WORKDIR /bot

COPY ./build/libs/bot-*.jar .

RUN rm -f bot-*-plain.jar

RUN mv bot-*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
