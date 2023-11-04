package com.microservice.pixmicroservice.api.consumers;

import com.microservice.pixmicroservice.api.models.ImmediateChargeDTO;
import com.microservice.pixmicroservice.api.models.Inputs.ImmediateChargeInputDTO;
import com.microservice.pixmicroservice.domain.services.GerenciaNetService;
import com.microservice.pixmicroservice.domain.services.GsonService;
import com.microservice.pixmicroservice.domain.services.PixService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.logging.Level;
import java.util.logging.Logger;

//@Service
public class PixConsumer {
    public static final Logger LOGGER = Logger.getLogger(GerenciaNetService.class.getName());

    @Autowired
    private PixService pixService;

    @Autowired
    private GsonService gsonService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void createImmediateChage(
            @Payload() ImmediateChargeInputDTO immediateChargeInputDTO
    ) {
        try {
//            ImmediateChargeInputDTO immediateChargeInputDTO = gsonService.fromJson(payload, ImmediateChargeInputDTO.class);
            ImmediateChargeDTO immediateChargeDTO = pixService.createImmediateCharge(immediateChargeInputDTO);
//            return ResponseEntity.ok(immediateChargeDTO);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
