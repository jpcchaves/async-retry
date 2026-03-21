package br.com.jpcchaves.retry.servicea.adapter.input.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class ExampleRequestDTO {

    @NotBlank
    private String name;

    @CPF
    @NotBlank
    private String cpf;
}
