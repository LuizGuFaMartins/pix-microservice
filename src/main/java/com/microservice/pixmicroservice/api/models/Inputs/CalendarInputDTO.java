package com.microservice.pixmicroservice.api.models.Inputs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalendarInputDTO {
    @NotNull(message = "O tempo de expiração não pode ser vazio")
    private int expiracao;
}
