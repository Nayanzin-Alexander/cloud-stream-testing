package com.nayanzin.cloudstreamtesting.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsumerApplication.class)
public class ConsumerApplicationTest {

    @Autowired
    private Sink channels;

    @Autowired
    private MessageCollector collector;

    @SpyBean
    private ConsumerApplication spyApp;

    @Test
    public void consumerTest() {

        // Given message.
        Message<String> msg = MessageBuilder
                .withPayload("abc")
                .setHeader("foo", "bar")
                .build();

        // When send message.
        channels.input().send(msg);
    }
}