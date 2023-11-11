package com.microservice.pixmicroservice.domain.services;

import com.microservice.pixmicroservice.api.models.ImmediateChargeDTO;
import com.microservice.pixmicroservice.api.models.Inputs.ImmediateChargeInputDTO;
import com.microservice.pixmicroservice.api.models.PaymentDTO;
import com.microservice.pixmicroservice.api.models.QrCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PixService {
    public static final Logger LOGGER = Logger.getLogger(GerenciaNetService.class.getName());

    @Autowired
    private GsonService gsonService;

    @Autowired
    private GerenciaNetService gerenciaNetService;

    @Autowired
    private HttpService httpService;

    public ImmediateChargeDTO createImmediateCharge(ImmediateChargeInputDTO immediateChargeInputDTO) throws Exception {
        try {
            String inputDto = gsonService.toJson(immediateChargeInputDTO);
            HashMap<String, Object> jsonMapInputDto = gsonService.fromJsonToMap(inputDto);

            Map<String, Object> immediateCharge = gerenciaNetService.createImmediateCharge(jsonMapInputDto);
            String immediateChargeJson = gsonService.toJson(immediateCharge);
            ImmediateChargeDTO immediateChargeDTO = gsonService.fromJson(immediateChargeJson, ImmediateChargeDTO.class);

            Map<String, Object> qrCode = gerenciaNetService.retrieveQrCode(immediateCharge);
            String qrCodeJson = gsonService.toJson(qrCode);
            QrCodeDTO qrCodeDTO = gsonService.fromJson(qrCodeJson, QrCodeDTO.class);

            immediateChargeDTO.setQrCode(qrCodeDTO);

            return immediateChargeDTO;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void sendImmediateChargeToReceiver(String receiverUrl, ImmediateChargeDTO immediateChargeDTO) {
        try {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentId(immediateChargeDTO.getTxid());
            paymentDTO.setPaymentName(immediateChargeDTO.getDevedor().getNome());
            paymentDTO.setPaymentCpf(immediateChargeDTO.getDevedor().getCpf());
            paymentDTO.setPaymentPrice(immediateChargeDTO.getValor().getOriginal());
            paymentDTO.setPaymentStatus(immediateChargeDTO.getStatus());
            paymentDTO.setPaymentQrCode(immediateChargeDTO.getQrCode().getLinkVisualizacao());
            String dateString = immediateChargeDTO.getCalendario().getCriacao();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            Date date = sdf.parse(dateString);
            paymentDTO.setPaymentCreatedAt(date);
            paymentDTO.setClientId(immediateChargeDTO.getClientId());
            paymentDTO.setOrderId(immediateChargeDTO.getOrderId());
            httpService.post(receiverUrl, paymentDTO);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
