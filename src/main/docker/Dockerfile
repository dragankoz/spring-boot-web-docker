FROM openjdk:8u131-jre-alpine
RUN apk add --no-cache 'su-exec>=0.2'
COPY /maven/ROOT.war /usr/local/ROOT.war
COPY dockerentrypoint.sh /usr/local/dockerentrypoint.sh
RUN chmod +x /usr/local/dockerentrypoint.sh
EXPOSE 8080
ENTRYPOINT ["/usr/local/dockerentrypoint.sh"]