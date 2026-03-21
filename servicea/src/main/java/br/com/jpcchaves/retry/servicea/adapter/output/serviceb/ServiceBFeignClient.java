package br.com.jpcchaves.retry.servicea.adapter.output.serviceb;

import br.com.jpcchaves.retry.servicea.adapter.input.dto.ExampleRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ServiceBFeignClient", url = "${services.serviceb.url}")
public interface ServiceBFeignClient {

    @PostMapping
    void processExampleServiceB(@RequestBody ExampleRequestDTO requestDTO);
}
