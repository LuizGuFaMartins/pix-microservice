package com.microservice.pixmicroservice.api.producers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessageChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@Service
public class PixProducer {
    private static final Logger logger = LoggerFactory.getLogger(PixProducer.class);

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    @Autowired
    private final AmazonSQSAsync amazonSqs;

    public PixProducer(final AmazonSQSAsync amazonSQSAsync) {
        this.amazonSqs = amazonSQSAsync;
    }

    public boolean send(final String messagePayload) {
        MessageChannel messageChannel = new QueueMessageChannel(amazonSqs, queueUrl);

        Message<String> msg = MessageBuilder.withPayload(messagePayload)
                .setHeader("sender", "app1")
                .setHeaderIfAbsent("country", "AE")
                .build();

        long waitTimeoutMillis = 5000;
        boolean sentStatus = messageChannel.send(msg, waitTimeoutMillis);
        logger.info("Immediate charge sent");
        return sentStatus;
    }

    public void sendToFIFOQueue(final String messagePayload) {
        String messageDeduplicationId = UUID.randomUUID().toString();
        String messageGroupId = UUID.randomUUID().toString();
        try {
            logger.info("Sending message to SQS queue on URL {}", queueUrl);
            amazonSqs.sendMessage(new SendMessageRequest(queueUrl, messagePayload)
                    .withMessageGroupId(messageGroupId).withMessageDeduplicationId(messageDeduplicationId));

            logger.info("Immediate charge sent");

        } catch (Exception ex) {
            logger.error("Error Message: {}", ex.getMessage());
        }
    }

}