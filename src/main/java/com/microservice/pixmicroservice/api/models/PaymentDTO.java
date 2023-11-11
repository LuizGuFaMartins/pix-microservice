package com.microservice.pixmicroservice.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {
    private String paymentId;
    private String paymentName;
    private String paymentCpf;
    private String paymentPrice;
    private String paymentStatus;
    private String paymentQrCode;
    private Date paymentCreatedAt;
    private Long clientId;
    private Long orderId;
}
