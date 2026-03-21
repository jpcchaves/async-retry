package br.com.jpcchaves.retry.servicea.domain.usecase;

import br.com.jpcchaves.retry.servicea.adapter.input.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.servicea.port.input.ServiceaInputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceaUseCase implements ServiceaInputPort {

    @Override
    public void processExample(ExampleRequestDTO requestDTO) {
        log.info("Processing example. Request: {}", requestDTO);
    }
}
