package br.com.jpcchaves.retry.serviceb.adapter.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class ExampleRequestDTO {

    @NotBlank
    private String name;

    @CPF
    @NotBlank
    private String cpf;

    @NotNull
    private Boolean simulateException = false;
}
