package com.nayanzin.cloudstreamtesting.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.util.concurrent.atomic.AtomicBoolean;

@EnableBinding(Source.class)
@SpringBootApplication
public class ProducerApplication {

    private AtomicBoolean semaphore = new AtomicBoolean(true);

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    @InboundChannelAdapter(channel = Source.OUTPUT, poller = @Poller(fixedDelay = "1000"))
    public MessageSource<String> produceFooBarMessage() {
        return () -> new GenericMessage<>(
                semaphore.getAndSet(!semaphore.get()) ? "foo" : "bar");
    }
}
