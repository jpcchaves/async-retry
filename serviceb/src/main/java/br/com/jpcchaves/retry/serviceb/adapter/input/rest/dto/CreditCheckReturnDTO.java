package br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto;

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
    private Boolean eligible;
}
