package br.com.jpcchaves.retry.serviceb.adapter.input.rest;

import br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto.CreditCheckReturnDTO;
import br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto.ExampleRequestDTO;
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
@RequestMapping("/api/v1/serviceb")
public class ServicebController {


    @PostMapping
    public ResponseEntity<CreditCheckReturnDTO> getCreditCheck(@RequestBody ExampleRequestDTO requestDTO) {
        log.info("Credit check request received. Request: {}", requestDTO);

        log.info("Credit check request processed. Response: {}", "");
        return ResponseEntity.ok().build();
    }
}
