package com.nayanzin.cloudstreamtesting.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
@EnableBinding(Processor.class)
public class ToUpperCaseGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToUpperCaseGatewayApplication.class, args);
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public String toUpperCase(String payload) {
        return payload.toUpperCase();
    }
}
