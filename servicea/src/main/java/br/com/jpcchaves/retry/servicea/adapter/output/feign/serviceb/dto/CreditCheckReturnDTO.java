package br.com.jpcchaves.retry.servicea.adapter.output.feign.serviceb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCheckReturnDTO {

    private String name;
    private String cpf;
    private boolean eligible;
}
