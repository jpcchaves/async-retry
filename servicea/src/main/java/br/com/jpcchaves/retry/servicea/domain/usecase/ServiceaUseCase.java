package br.com.jpcchaves.retry.servicea.domain.usecase;

import br.com.jpcchaves.retry.servicea.adapter.input.rest.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.servicea.domain.model.RetryMqEventModel;
import br.com.jpcchaves.retry.servicea.domain.model.enums.RetryDuration;
import br.com.jpcchaves.retry.servicea.port.input.ServiceaInputPort;
import br.com.jpcchaves.retry.servicea.port.output.MqRetryOutputPort;
import br.com.jpcchaves.retry.servicea.port.output.ServicebOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static br.com.jpcchaves.retry.servicea.config.RabbitMqRetryConfig.RETRY_EXCHANGE_NAME;
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
        } catch (Exception ex) {
            log.error("An error occurred while processing example. Request: {}", requestDTO, ex);
            triggerAsyncRetry(requestDTO, null, ex, 1, FIFTY_MINUTES.getDurationMs());
        }
    }

    @Override
    public void processRetryExample(RetryMqEventModel<ExampleRequestDTO> eventMessage) {
        log.info("Processing retry. Request: {}", eventMessage);
        try {
            servicebOutputPort.processExampleServiceB(eventMessage.getPayload());
        } catch (Exception ex) {
            log.error("An error occurred while processing retry. Request: {}", eventMessage, ex);
            var currentAttempt = eventMessage.getAttemptCount() + 1;

            log.info("Current attempt count {}", currentAttempt);

            if (currentAttempt > 3) {
                // send to DLQ
                log.info("Maximum attempts reached. Sending message to DLQ: {}", eventMessage);
                return;
            }

            var delayMs = RetryDuration.getDelayFromRetryCount(currentAttempt);
            log.info("Next retry will have delay {} ms", delayMs);

            triggerAsyncRetry(eventMessage.getPayload(), eventMessage, ex, currentAttempt, delayMs);
        }
    }

    @Async
    private void triggerAsyncRetry(
            ExampleRequestDTO requestDTO,
            RetryMqEventModel<ExampleRequestDTO> eventMessage,
            Exception ex,
            int currentAttempt,
            int delayMs
    ) {
        executorService.execute(() -> {
            log.info("Executing async retry. Request: {}", requestDTO);
            var event = RetryMqEventModel.<ExampleRequestDTO>builder()
                    .eventId(UUID.randomUUID().toString())
                    .attemptCount(currentAttempt)
                    .lastException(ex)
                    .payload(requestDTO)
                    .exceptions(new ArrayList<>())
                    .build();

            if (eventMessage != null) {
                event.getExceptions().addAll(eventMessage.getExceptions());
            }

            event.getExceptions().add(ex);

            mqRetryOutputPort.sendWithDelay(RETRY_EXCHANGE_NAME, event, delayMs);
        });
    }
}
