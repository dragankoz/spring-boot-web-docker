#!/bin/sh
set -e

adduser app -D
chown app:app /usr/local/*
JAVA_OPTS=${JAVA_OPTS:="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -XshowSettings:vm"}
exec su-exec app java ${JAVA_OPTS} -jar /usr/local/ROOT.war --server.port 8080