package com.nayanzin.cloudstreamtesting.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BlockingQueue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class ProducerTest {

    @Autowired
    private Source channels;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void testFooBarMessageProducer() {
        BlockingQueue<Message<?>> queue = messageCollector.forChannel(channels.output());

        assertThat(queue, receivesPayloadThat(is("foo")));
        assertThat(queue, receivesPayloadThat(is("bar")));
        assertThat(queue, receivesPayloadThat(is("foo")));
        assertThat(queue, receivesPayloadThat(is("bar")));
    }
}