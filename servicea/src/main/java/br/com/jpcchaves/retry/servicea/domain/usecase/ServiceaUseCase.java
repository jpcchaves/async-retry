package br.com.jpcchaves.retry.servicea.domain.usecase;

import br.com.jpcchaves.retry.servicea.adapter.input.rest.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.servicea.domain.model.RetryMqEventModel;
import br.com.jpcchaves.retry.servicea.port.input.ServiceaInputPort;
import br.com.jpcchaves.retry.servicea.port.output.MqRetryOutputPort;
import br.com.jpcchaves.retry.servicea.port.output.ServicebOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static br.com.jpcchaves.retry.servicea.config.RabbitMqConfig.RETRY_EXCHANGE_NAME;
import static br.com.jpcchaves.retry.servicea.domain.model.enums.RetryDuration.FIFTY_MINUTES;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceaUseCase implements ServiceaInputPort {

    private final ServicebOutputPort servicebOutputPort;
    private final MqRetryOutputPort mqRetryOutputPort;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void processExample(ExampleRequestDTO requestDTO) {
        log.info("Processing example. Request: {}", requestDTO);
        try {
            servicebOutputPort.processExampleServiceB(requestDTO);
        } catch (RuntimeException ex) {
            log.error("An error ocurred while processing example. Request: {}", requestDTO, ex);
            triggerAsyncRetry(requestDTO, ex);
        }
    }

    @Async
    private void triggerAsyncRetry(ExampleRequestDTO requestDTO, RuntimeException ex) {
        executorService.execute(() -> {
            var event = new RetryMqEventModel<ExampleRequestDTO>();

            event.setEventId(UUID.randomUUID().toString());
            event.setPayload(requestDTO);
            event.setAttemptCount(1);
            event.setLastException(ex);
            event.getExceptions().add(ex);

            mqRetryOutputPort.sendWithDelay(RETRY_EXCHANGE_NAME, event, FIFTY_MINUTES.getDurationMs());
        });
    }
}
