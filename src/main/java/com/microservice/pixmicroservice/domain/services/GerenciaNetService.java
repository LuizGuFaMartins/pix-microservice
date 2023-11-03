package com.microservice.pixmicroservice.domain.services;

import br.com.gerencianet.gnsdk.Gerencianet;
import com.microservice.pixmicroservice.api.configurations.GerenciaNetConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GerenciaNetService {

    public static final Logger LOGGER = Logger.getLogger(GerenciaNetService.class.getName());
    public Map<String, Object> createImmediateCharge(HashMap<String, Object> body) throws Exception {
        try {
            Gerencianet gn = new Gerencianet(GerenciaNetConfig.getCredentials());
            return gn.call("pixCreateImmediateCharge", new HashMap<String, String>(), body);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public Map<String, Object> retrieveQrCode(Map<String, Object> charge) throws Exception {
        try {
            Gerencianet gn = new Gerencianet(GerenciaNetConfig.getCredentials());
            Map<String, Object> chargeLoc = (Map<String, Object>) charge.get("loc");
            String chargeId = String.valueOf(chargeLoc.get("id"));
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("id", chargeId);
            return gn.call("pixGenerateQRCode", params, new HashMap<String, Object>());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

    }
}