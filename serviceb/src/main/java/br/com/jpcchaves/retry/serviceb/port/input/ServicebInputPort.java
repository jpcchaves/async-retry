package br.com.jpcchaves.retry.serviceb.port.input;

import br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto.CreditCheckReturnDTO;
import br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto.ExampleRequestDTO;

public interface ServicebInputPort {
    CreditCheckReturnDTO getCreditCheck(ExampleRequestDTO requestDTO);
}
