# SpringBoot+Web with Docker and Cucumber Test Integration

### Purpose

Useful as a *template* project to get you going quickly with docker and integration tests using docker containers.

### Prereqisites

- Java 8.x
- Maven 3.x
- (Optional) Docker installed locally and DOCKER_HOST environment variable set,

Linux
```
export DOCKER_HOST=unix:///var/run/docker.sock
```

Windows
```
set DOCKER_HOST=npipe:////./pipe/docker_engine
```

Build

```
mvn clean verify
```


### Notes
- See https://dmp.fabric8.io/ for details documentation for the docker-maven-plugin.
- See this blog for an explaination on optimizing/aligning jvm memory vs docker container assigned memory,
https://blog.csanchez.org/2017/05/31/running-a-jvm-in-a-container-without-getting-killed/



