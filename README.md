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

### HTTP to JMS
$ curl -X POST http://localhost:8080/direct/to/jms -d 'hello world'
HELLO+WORLD=

$ curl -X POST http://localhost:8080/async/to/jms -d 'hello world'
hello+world=

### REST ERQUEST -> JMS -> SOAP -> JMS -> REST RESPONSE
* Test SOAP first - note response payload is XML
$ curl --header "content-type: text/xml" http://localhost:8080/ws -d \
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gs="http://sungung.com/ws"><soapenv:Header/><soapenv:Body><gs:getCountryRequest><gs:name>Australia</gs:name></gs:getCountryRequest></soapenv:Body></soapenv:Envelope>'
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header/><SOAP-ENV:Body><ns2:getCountryResponse xmlns:ns2="http://sungung.com/ws"><ns2:country><ns2:name>Australia</ns2:name><ns2:population>1000</ns2:population><ns2:capital>Canberra</ns2:capital><ns2:currency>AUD</ns2:currency></ns2:country></ns2:getCountryResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>

* Test REST service - note response payload is JSON
$ curl http://localhost:8080/api/rest/austrailia --header "content-type: application/json"
{"country":{"name":"Australia","population":1000,"capital":"Canberra","currency":"AUD"}}





