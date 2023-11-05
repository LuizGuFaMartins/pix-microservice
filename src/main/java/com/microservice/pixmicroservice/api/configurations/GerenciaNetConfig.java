package com.microservice.pixmicroservice.api.configurations;

import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class GerenciaNetConfig {
    @Value("${gerencianet.client-id}")
    private String clientId;
    @Value("${gerencianet.client-secret}")
    private String clientSecret;
    @Value("${gerencianet.certificate-path}")
    private String certificate;
    @Value("${gerencianet.sandbox}")
    private Boolean sandbox;

    @Getter
    private static JSONObject credentials;

    @PostConstruct
    private void initializeCredentials() {
        credentials = new JSONObject();
        credentials.put("client_id", clientId);
        credentials.put("client_secret", clientSecret);
        credentials.put("certificate", certificate);
        credentials.put("sandbox", sandbox);
    }
}
