package com.rabbitmq.producer.client;

import com.rabbitmq.producer.entity.RabbitmqQueue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.List;

@Service
public class RabbitmqClient {

    public List<RabbitmqQueue> getAllQueue() {
        WebClient webClient = WebClient.create("http://localhost:15672/api/queues");
        String basicAuth = createBasicAuth("guest", "guest");

        return webClient.get().header(HttpHeaders.AUTHORIZATION, basicAuth).retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RabbitmqQueue>>() {
                }).block(Duration.ofSeconds(10));
    }

    public String createBasicAuth(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
