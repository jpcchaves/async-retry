package br.com.jpcchaves.retry.servicea.port.output;

import br.com.jpcchaves.retry.servicea.adapter.input.dto.ExampleRequestDTO;

public interface ServicebOutputPort {
    void processExampleServiceB(ExampleRequestDTO requestDTO);
}
