package com.microservice.pixmicroservice.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class HttpService {

    @Autowired
    public GsonService gsonService;

    public void post(String url, Object body) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            String requestBody = gsonService.toJson(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            responseFuture.thenApply(response -> {
                int statusCode = response.statusCode();
                String responseBody = response.body();

                if (statusCode == 200) {
                    log.info("Receiver response: " + responseBody);
                } else {
                    log.error("Request to receiver failed " + statusCode);
                }
                return responseBody;
            }).join();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
