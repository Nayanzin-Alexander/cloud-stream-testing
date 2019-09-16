package com.nayanzin.cloudstreamtesting.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BlockingQueue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ToUpperCaseGatewayApplication.class)
public class GatewayApplicationTest {

    @SpyBean
    private ToUpperCaseGatewayApplication appSpy;

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector collector;

    @Test
    public void testToUpperCaseGateway() {
        // When message is sent to input channel
        processor.input().send(new GenericMessage<>("abc"));
        processor.input().send(new GenericMessage<>("ABC"));
        processor.input().send(new GenericMessage<>("XyZ"));

        // Then assert message is transforms to upper case and sends to output channel.
        BlockingQueue<Message<?>> queue = collector.forChannel(processor.output());
        assertThat(queue, receivesPayloadThat(is("ABC")));
        assertThat(queue, receivesPayloadThat(is("ABC")));
        assertThat(queue, receivesPayloadThat(is("XYZ")));

        // and assert method was invoked exactly 3 times.
        Mockito.verify(appSpy, times(3)).toUpperCase(anyString());
    }

}