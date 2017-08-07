FROM openjdk:8u131-jre-alpine
RUN apk add --no-cache 'su-exec>=0.2'
RUN adduser app -D -h /opt/app
COPY /maven/ROOT.war /opt/app/ROOT.war
COPY dockerentrypoint.sh /opt/app/dockerentrypoint.sh
RUN chown -R app:app /opt/app
RUN chmod +x /opt/app/dockerentrypoint.sh
EXPOSE 8080
ENTRYPOINT ["/opt/app/dockerentrypoint.sh"]