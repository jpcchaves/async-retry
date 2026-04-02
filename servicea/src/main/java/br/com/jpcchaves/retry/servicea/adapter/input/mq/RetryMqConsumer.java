package br.com.jpcchaves.retry.servicea.adapter.input.mq;

import br.com.jpcchaves.retry.servicea.adapter.input.rest.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.servicea.domain.model.RetryMqEventModel;
import br.com.jpcchaves.retry.servicea.port.input.ServiceaInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import static br.com.jpcchaves.retry.servicea.config.RabbitMqRetryConfig.RETRY_QUEUE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryMqConsumer {

    private final ObjectMapper objectMapper;
    private final ServiceaInputPort serviceaInputPort;

    @RabbitListener(queues = RETRY_QUEUE_NAME)
    public void consume(Message message) {
        log.info("Received retry message {}", message);

        var messageParsed = getMessageParsed(message);

        log.info("Message parsed {}", messageParsed);
        serviceaInputPort.processRetryExample(messageParsed);
    }

    private RetryMqEventModel<ExampleRequestDTO> getMessageParsed(Message message) {
        return objectMapper.readValue(message.getBody(), new TypeReference<>() {
        });
    }
}
