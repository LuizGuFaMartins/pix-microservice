package com.microservice.pixmicroservice.domain.services;

import com.microservice.pixmicroservice.api.models.ImmediateChargeDTO;
import com.microservice.pixmicroservice.api.models.Inputs.ImmediateChargeInputDTO;
import com.microservice.pixmicroservice.api.models.QrCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public void sendImmediateChargeToReceiver(String receiverUrl, ImmediateChargeDTO immediateChargeDTO){
        httpService.post(receiverUrl, immediateChargeDTO);
    }
}
