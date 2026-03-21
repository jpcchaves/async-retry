package br.com.jpcchaves.retry.servicea.port.output;

import br.com.jpcchaves.retry.servicea.domain.model.RetryMqEventModel;

public interface MqRetryOutputPort {

    void sendRetryToQueue(RetryMqEventModel retryMqEventModel);
}
