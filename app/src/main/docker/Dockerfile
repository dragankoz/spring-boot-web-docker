FROM openjdk:8u131-jre-alpine
ARG port
ARG artifact
ENV PORT=$port
ENV ARTIFACT=$artifact
RUN apk add --no-cache 'su-exec>=0.2'
RUN mkdir -p /opt/app
RUN adduser -h /opt/app -D app
RUN chown -R app:app /opt/app
COPY /maven/$artifact /opt/app/$artifact
ENV JAVA_OPTS "-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -XshowSettings:vm"
EXPOSE $port
CMD exec su-exec app java ${JAVA_OPTS} -jar /opt/app/${ARTIFACT}
