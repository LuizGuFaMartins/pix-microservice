package com.microservice.pixmicroservice.api.models.Inputs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImmediateChargeInputDTO {
    private CalendarInputDTO calendario;
    private DebtorInputDTO devedor;
    private ValueInputDTO valor;

    @NotNull(message = "A chave pix não pode ser vazia")
    private String chave;

    private String solicitacaoPagador;
    private Long clientId;
    private Long orderId;
}
