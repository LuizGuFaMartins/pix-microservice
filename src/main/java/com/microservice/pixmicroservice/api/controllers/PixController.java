package com.microservice.pixmicroservice.api.controllers;

import com.microservice.pixmicroservice.api.models.ImmediateChargeDTO;
import com.microservice.pixmicroservice.api.models.Inputs.ImmediateChargeInputDTO;
import com.microservice.pixmicroservice.domain.services.GerenciaNetService;
import com.microservice.pixmicroservice.domain.services.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class PixController {
    public static final Logger LOGGER = Logger.getLogger(GerenciaNetService.class.getName());

    @Autowired
    private PixService pixService;

    @PostMapping("/immediate-charge")
    public ResponseEntity<ImmediateChargeDTO> createImmediateChage(
            @RequestBody() ImmediateChargeInputDTO immediateChargeInputDTO
    ) {
        try {
            ImmediateChargeDTO immediateChargeDTO = pixService.createImmediateCharge(immediateChargeInputDTO);
            return ResponseEntity.ok(immediateChargeDTO);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
