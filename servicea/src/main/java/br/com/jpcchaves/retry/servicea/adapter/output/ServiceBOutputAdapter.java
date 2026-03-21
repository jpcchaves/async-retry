package br.com.jpcchaves.retry.servicea.adapter.output;

import br.com.jpcchaves.retry.servicea.adapter.input.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.servicea.port.output.ServicebOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static feign.FeignException.FeignServerException;

@Slf4j
@RequiredArgsConstructor
public class ServiceBOutputAdapter implements ServicebOutputPort {

    private final ServiceBFeignClient serviceBFeignClient;

    @Override
    public void processExampleServiceB(ExampleRequestDTO requestDTO) {
        log.info("Calling service B to process request from service A with request: {}", requestDTO);
        try {
            serviceBFeignClient.processExampleServiceB(requestDTO);
        } catch (FeignServerException serverException) {
            log.error("Error trying to call service B to process request from service A. Request: {}",
                    requestDTO, serverException);
            // TODO: add retry with rabbitmq
        }
    }
}
