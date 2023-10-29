package com.microservice.pixmicroservice.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DebtorDTO {
    private String cpf;
    private String cnpj;
    private String nome;
}
