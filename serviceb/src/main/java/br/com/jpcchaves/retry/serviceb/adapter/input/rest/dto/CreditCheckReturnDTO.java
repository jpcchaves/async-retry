package br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCheckReturnDTO {

    private String name;
    private String cpf;
    private boolean eligible;
}
