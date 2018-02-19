package com.sungung.httptojms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.dsl.jms.Jms;

@SpringBootApplication
@EnableIntegration
public class HttpToJmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpToJmsApplication.class, args);
	}
	
	@Bean
	public IntegrationFlow httpToJms(@Autowired ActiveMQConnectionFactory connectionFactory){
	    return IntegrationFlows.from(Http.inboundGateway("/direct/to/jms").requestMapping(m -> m.methods(HttpMethod.POST)).requestPayloadType(String.class))	            
	    		.handle(Jms.outboundGateway(connectionFactory).requestDestination("from.web"))
	    		.bridge(null)
	    		.get();	   		
	}
	
	@Bean
	public IntegrationFlow asyncHttpToJms(@Autowired ActiveMQConnectionFactory connectionFactory){
	    return IntegrationFlows.from(Http.inboundGateway("/async/to/jms").requestMapping(m -> m.methods(HttpMethod.POST)).requestPayloadType(String.class))	            
				.publishSubscribeChannel(subscribers -> {
					subscribers.subscribe(f -> f.handle(Jms.outboundGateway(connectionFactory).requestDestination("from.web")));
				})
				.bridge(null)
				.get();	 		
	}
	
	@Bean
	public IntegrationFlow listen(@Autowired ActiveMQConnectionFactory connectionFactory){
		return IntegrationFlows.from(Jms.inboundGateway(connectionFactory).destination("from.web"))
				.<String, String>transform(String::toUpperCase).log().bridge(null).get();
	}

		
}
