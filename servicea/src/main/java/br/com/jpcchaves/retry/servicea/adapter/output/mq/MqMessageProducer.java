package br.com.jpcchaves.retry.servicea.adapter.output.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void produce(String exchangeName, String routingKey, String payload) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, payload);
            log.info("Message sent to exchange {} and routingKey {} with message: {}", exchangeName, routingKey, payload);
        } catch (Exception ex) {
            log.error("Error while send message to exchange {} and routingKey {} with message: {}", exchangeName, routingKey, payload, ex);
            throw ex;
        }
    }

    public void produceWithDelay(String exchangeName, String routingKey, String payload, long delayMs) {
        var message = MessageBuilder.withBody(payload.getBytes(StandardCharsets.UTF_8)).setHeader("x-delay", delayMs).build();
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
            log.info("Message sent to exchange {} and routingKey {} with delay {} and scheduled to be consumed at {} and message {}",
                    exchangeName, routingKey, delayMs, LocalDateTime.now().plus(delayMs, ChronoUnit.MILLIS), payload);
        } catch (Exception ex) {
            log.error("Error while send message to exchange {} and routingKey {} with message: {}", exchangeName, routingKey, message, ex);
            throw ex;
        }
    }
}
