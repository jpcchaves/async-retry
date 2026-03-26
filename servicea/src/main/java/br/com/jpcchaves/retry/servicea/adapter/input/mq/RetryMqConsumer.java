package br.com.jpcchaves.retry.servicea.adapter.input.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static br.com.jpcchaves.retry.servicea.config.RabbitMqConfig.RETRY_QUEUE_NAME;

@Slf4j
@Component
public class RetryMqConsumer {

    @RabbitListener(queues = RETRY_QUEUE_NAME)
    public void consume(Message message) {
        log.info("Received retry message {}", message);
    }
}
