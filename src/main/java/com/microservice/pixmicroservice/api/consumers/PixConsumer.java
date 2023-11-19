package com.microservice.pixmicroservice.api.consumers;

import com.microservice.pixmicroservice.api.models.ImmediateChargeDTO;
import com.microservice.pixmicroservice.api.models.Inputs.ImmediateChargeInputDTO;
import com.microservice.pixmicroservice.domain.services.GsonService;
import com.microservice.pixmicroservice.domain.services.PixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.logging.Logger;

@Slf4j
@Service
public class PixConsumer {
    public static final Logger LOGGER = Logger.getLogger(PixConsumer.class.getName());

    @Value("${queue.receiver.url}")
    public String receiverUrl;

    @Autowired
    private PixService pixService;

    @Autowired
    private GsonService gsonService;

    @SqsListener(value = "${aws.sqs.queueUrl}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveStringMessage(final String message,
                                     @Header("SenderId") String senderId) {
        log.info("message received {} {}", senderId, message);
        if (Objects.nonNull(message)) {
            ImmediateChargeInputDTO immediateChargeInputDTO = gsonService.fromJson(message, ImmediateChargeInputDTO.class);
            Long clientId = immediateChargeInputDTO.getClientId();
            Long orderId = immediateChargeInputDTO.getOrderId();
            immediateChargeInputDTO.setClientId(null);
            immediateChargeInputDTO.setOrderId(null);
            try {
                ImmediateChargeDTO immediateChargeDTO = pixService.createImmediateCharge(immediateChargeInputDTO);
                if (Objects.nonNull(receiverUrl)) {
                    immediateChargeDTO.setClientId(clientId);
                    immediateChargeDTO.setOrderId(orderId);
                    pixService.sendImmediateChargeToReceiver(receiverUrl, immediateChargeDTO);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
