package br.com.jpcchaves.retry.servicea.adapter.output.feign.serviceb;

import br.com.jpcchaves.retry.servicea.adapter.input.rest.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.servicea.port.output.ServicebOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceBOutputAdapter implements ServicebOutputPort {

    private final ServiceBFeignClient serviceBFeignClient;

    @Override
    public void processExampleServiceB(ExampleRequestDTO requestDTO) {
        log.info("Calling service B to process request from service A with request: {}", requestDTO);
        try {
            serviceBFeignClient.processExampleServiceB(requestDTO);
        } catch (Exception serverException) {
            log.error("Error trying to call service B to process request from service A. Request: {}",
                    requestDTO, serverException);
            throw serverException;
        }
    }
}
