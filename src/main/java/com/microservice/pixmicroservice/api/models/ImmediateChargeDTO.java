package com.microservice.pixmicroservice.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImmediateChargeDTO {
    private CalendarDTO calendario;
    private String txid;
    private int revisao;
    private LocDTO loc;
    private String location;
    private String status;
    private DebtorDTO devedor;
    private ValueDTO valor;
    private String chave;
    private String solicitacaoPagador;
    private QrCodeDTO qrCode;
}
