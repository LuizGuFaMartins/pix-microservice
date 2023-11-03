package com.microservice.pixmicroservice.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QrCodeDTO {
    public String imagemQrcode;
    public String qrcode;
    public String linkVisualizacao;
}
