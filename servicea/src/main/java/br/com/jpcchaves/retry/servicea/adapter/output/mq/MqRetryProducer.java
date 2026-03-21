package br.com.jpcchaves.retry.servicea.adapter.output.mq;

import br.com.jpcchaves.retry.servicea.domain.model.RetryMqEventModel;
import br.com.jpcchaves.retry.servicea.port.output.MqRetryOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import static br.com.jpcchaves.retry.servicea.config.RabbitMqConfig.RETRY_LISTENER_QUEUE_NAME;

@Component
@RequiredArgsConstructor
public class MqRetryProducer implements MqRetryOutputPort {

    private final MqMessageProducer mqMessageProducer;
    private final ObjectMapper objectMapper;

    @Override
    public void sendRetryToQueue(RetryMqEventModel retryMqEventModel) {
        mqMessageProducer.produce(RETRY_LISTENER_QUEUE_NAME, objectMapper.writeValueAsString(retryMqEventModel));
    }
}
