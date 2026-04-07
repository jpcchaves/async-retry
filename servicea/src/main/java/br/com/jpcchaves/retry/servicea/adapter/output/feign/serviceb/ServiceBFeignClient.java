package br.com.jpcchaves.retry.servicea.adapter.output.feign.serviceb;

import br.com.jpcchaves.retry.servicea.adapter.input.rest.dto.ExampleRequestDTO;
import br.com.jpcchaves.retry.servicea.adapter.output.feign.serviceb.dto.CreditCheckReturnDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ServiceBFeignClient", url = "${services.serviceb.url}")
public interface ServiceBFeignClient {

    @PostMapping("/api/v1/serviceb")
    CreditCheckReturnDTO processExampleServiceB(@RequestBody ExampleRequestDTO requestDTO);
}
