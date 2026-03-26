package br.com.jpcchaves.retry.servicea.port.input;

import br.com.jpcchaves.retry.servicea.adapter.input.rest.dto.ExampleRequestDTO;

public interface ServiceaInputPort {
    void processExample(ExampleRequestDTO requestDTO);
}
