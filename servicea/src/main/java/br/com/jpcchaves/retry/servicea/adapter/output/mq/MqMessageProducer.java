package br.com.jpcchaves.retry.servicea.adapter.output.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static br.com.jpcchaves.retry.servicea.config.RabbitMqRetryConfig.RETRY_ROUTING_KEY;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void produce(String route, Object event) {
        rabbitTemplate.convertAndSend(route, event);
    }

    public void produceWithDelay(String exchangeName, String payload, long delayMs) {
        var message = MessageBuilder.withBody(payload.getBytes(StandardCharsets.UTF_8)).setHeader("x-delay", delayMs).build();
        try {
            rabbitTemplate.convertAndSend(exchangeName, RETRY_ROUTING_KEY, message);
            log.info("Message sent to {} with delay {} and message: {}", exchangeName, delayMs, payload);
        } catch (Exception ex) {
            log.error("Error while send message to {} with message: {}", exchangeName, message, ex);
            throw ex;
        }
    }
}
