FROM openjdk:8u131-jre-alpine
ARG port
ARG artifact
ENV PORT=$port
ENV ARTIFACT=$artifact
RUN apk add --no-cache 'su-exec>=0.2'
RUN adduser app -D -h /opt/app
COPY /maven/$artifact /opt/app/$artifact
RUN chown -R app:app /opt/app
EXPOSE $port
CMD exec su-exec app java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -XshowSettings:vm -jar /opt/app/${ARTIFACT} --server.port ${PORT}