# Spring integration - Http Request -> Jms -> Http Response

## Getting started
There is pattern you want to delegate businss logic into another layer and forget Or getting feedback from the other layer and repackaged into the http response.
 * http request -> Jms 
                -> Return whatever status in Jms
 * http request -> Jms -> http response

## Prerequisites

### Download Springboot archetype
```
curl https://start.spring.io/starter.zip \
-d dependencies=web,integration,activemq,devtools \
-d groupId=com.sung \
-d artifactId=httptojms \
-d name=httptojms \
-d description="Http service with JMS delegation" \
-d baseDir=httptojms \
-o httptojms.zip
```

## Build & Running Application
git clone
mvn spring-boot:run

## Test
$ curl -X POST http://localhost:8080/direct/to/jms -d 'hello world'
HELLO+WORLD=

$ curl -X POST http://localhost:8080/async/to/jms -d 'hello world'
hello+world=

