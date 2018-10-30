FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh yupi
WORKDIR /home/yupi

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown yupi:yupi entrypoint.sh
USER yupi

RUN mkdir /home/yupi/config

ADD *.war app.war

VOLUME ["/home/yupi/config"]

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8080

