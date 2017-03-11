
spring-boot-swagger-demo
========
- gradle
- spring boot/jpa
- sqlite
- jetty
- swagger
- swagger-spring-rest



## Development

### Spring Boot
```
$ ./gradlew bootRun
```
### Build & Run
```
$ ./gradlew build && java -jar build/libs/tbk-spring-boot-swagger-demo-<version>.jar
```

```
$ curl localhost:8080/health
{"status":"UP","diskSpace":{"status":"UP","total":397635555328,"free":328389529600,"threshold":10485760}}}
```

