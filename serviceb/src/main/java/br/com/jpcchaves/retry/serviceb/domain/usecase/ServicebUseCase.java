package br.com.jpcchaves.retry.serviceb.domain.usecase;

import br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto.CreditCheckReturnDTO;
import br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.serviceb.port.input.ServicebInputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class ServicebUseCase implements ServicebInputPort {

    @Override
    public CreditCheckReturnDTO getCreditCheck(ExampleRequestDTO requestDTO) {
        log.info("Executing credit check with Request: {}", requestDTO);
        if (Boolean.TRUE.equals(requestDTO.getSimulateException())) {
            log.error("An exception occurred in serviceB credit check. Request: {}", requestDTO);

            var exceptionMessage = "An exception occurred in serviceB credit check. Request: %s".formatted(requestDTO);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exceptionMessage);
        }

        return getRandomCreditCheck(requestDTO);
    }

    private CreditCheckReturnDTO getRandomCreditCheck(ExampleRequestDTO requestDTO) {
        return CreditCheckReturnDTO.builder()
                .cpf(requestDTO.getCpf())
                .name(requestDTO.getName())
                .eligible(Math.random() < 0.5)
                .build();
    }
}


