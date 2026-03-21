package br.com.jpcchaves.retry.servicea.adapter.input;

import br.com.jpcchaves.retry.servicea.adapter.input.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.servicea.port.input.ServiceaInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/servicea")
public class ServiceaController {

    private final ServiceaInputPort serviceaInputPort;

    @PostMapping("/example")
    public ResponseEntity<?> example(@RequestBody ExampleRequestDTO requestDTO) {
        log.info("Receiving example request. Request: {}", requestDTO);
        serviceaInputPort.processExample(requestDTO);
        log.info("Example request processed. Request: {}", requestDTO);
        return ResponseEntity.accepted().build();
    }
}
