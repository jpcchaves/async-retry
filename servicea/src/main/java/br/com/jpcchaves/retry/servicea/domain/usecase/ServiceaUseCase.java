package br.com.jpcchaves.retry.servicea.domain.usecase;

import br.com.jpcchaves.retry.servicea.adapter.input.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.servicea.domain.model.RetryMqEventModel;
import br.com.jpcchaves.retry.servicea.port.input.ServiceaInputPort;
import br.com.jpcchaves.retry.servicea.port.output.MqRetryOutputPort;
import br.com.jpcchaves.retry.servicea.port.output.ServicebOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceaUseCase implements ServiceaInputPort {

    private final ServicebOutputPort servicebOutputPort;
    private final MqRetryOutputPort mqRetryOutputPort;

    @Override
    public void processExample(ExampleRequestDTO requestDTO) {
        log.info("Processing example. Request: {}", requestDTO);
        try {
            servicebOutputPort.processExampleServiceB(requestDTO);
        } catch (RuntimeException ex) {
            var event = new RetryMqEventModel();

            event.setEventId(UUID.randomUUID().toString());
            event.setPayload(requestDTO);
            event.setRetryCount(1);

            mqRetryOutputPort.sendRetryToQueue(event);
        }
    }
}
