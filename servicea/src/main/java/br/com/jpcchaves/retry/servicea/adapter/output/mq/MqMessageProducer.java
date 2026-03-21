package br.com.jpcchaves.retry.servicea.adapter.output.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void produce(String route, Object event) {
        rabbitTemplate.convertAndSend(route, event);
    }
}
