package br.com.jpcchaves.retry.servicea.port.output;

import br.com.jpcchaves.retry.servicea.domain.model.RetryMqEventModel;

public interface MqRetryOutputPort {

    <T> void send(String exchangeName, String routingKey, RetryMqEventModel<T> message);

    <T> void sendWithDelay(String exchangeName, String routingKey, RetryMqEventModel<T> message, long delayMs);
}
