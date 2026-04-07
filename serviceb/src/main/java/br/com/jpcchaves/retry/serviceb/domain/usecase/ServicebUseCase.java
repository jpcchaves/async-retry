package br.com.jpcchaves.retry.serviceb.domain.usecase;

import br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto.CreditCheckReturnDTO;
import br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.serviceb.port.input.ServicebInputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServicebUseCase implements ServicebInputPort {

    @Override
    public CreditCheckReturnDTO getCreditCheck(ExampleRequestDTO requestDTO) {
        if (requestDTO.getSimulateException())
            throw new RuntimeException("An exception occurred in serviceB credit check. Request: %s".formatted(requestDTO));

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


