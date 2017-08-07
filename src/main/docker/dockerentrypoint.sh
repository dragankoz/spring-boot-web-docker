#!/bin/sh
set -e
JAVA_OPTS=${JAVA_OPTS:="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -XshowSettings:vm"}
exec su-exec app java ${JAVA_OPTS} -jar /opt/app/ROOT.war --server.port 8080