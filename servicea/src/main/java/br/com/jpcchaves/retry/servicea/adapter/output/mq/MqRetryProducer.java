package br.com.jpcchaves.retry.servicea.adapter.output.mq;

import br.com.jpcchaves.retry.servicea.domain.model.RetryMqEventModel;
import br.com.jpcchaves.retry.servicea.port.output.MqRetryOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class MqRetryProducer implements MqRetryOutputPort {

    private final MqMessageProducer mqMessageProducer;
    private final ObjectMapper objectMapper;

    @Override
    public <T> void send(String exchangeName, String routingKey, RetryMqEventModel<T> message) {
        mqMessageProducer.produce(exchangeName, routingKey, objectMapper.writeValueAsString(message));
    }

    @Override
    public <T> void sendWithDelay(String exchangeName, String routingKey, RetryMqEventModel<T> message, long delayMs) {
        mqMessageProducer.produceWithDelay(exchangeName, routingKey, objectMapper.writeValueAsString(message), delayMs);
    }
}
