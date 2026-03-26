package br.com.jpcchaves.retry.servicea.port.output;

import br.com.jpcchaves.retry.servicea.adapter.input.rest.dto.ExampleRequestDTO;

public interface ServicebOutputPort {
    void processExampleServiceB(ExampleRequestDTO requestDTO);
}
