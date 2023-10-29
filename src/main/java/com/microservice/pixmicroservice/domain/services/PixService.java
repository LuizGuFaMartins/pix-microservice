package com.microservice.pixmicroservice.domain.services;

import com.microservice.pixmicroservice.api.models.ImmediateChargeDTO;
import com.microservice.pixmicroservice.api.models.Inputs.ImmediateChargeInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ImmediateChargeDTO createImmediateCharge(ImmediateChargeInputDTO immediateChargeInputDTO) throws Exception {
        try {
            String json = gsonService.toJson(immediateChargeInputDTO);
            HashMap<String, Object> jsonMap = gsonService.fromJsonToMap(json);
            Map<String, Object> immediateCharge = gerenciaNetService.createImmediateCharge(jsonMap);
            String immediateChargeJson = gsonService.toJson(immediateCharge);
            return gsonService.fromJson(immediateChargeJson, ImmediateChargeDTO.class);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
