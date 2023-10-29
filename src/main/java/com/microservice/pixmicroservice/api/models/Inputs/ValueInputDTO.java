package com.microservice.pixmicroservice.api.models.Inputs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValueInputDTO {
    @NotNull(message = "O valor da cobrança deve ser informado")
    private String original;
}
