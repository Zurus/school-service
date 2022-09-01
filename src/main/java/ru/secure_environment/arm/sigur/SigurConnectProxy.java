package ru.secure_environment.arm.sigur;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Компонент для взаимодействия с БД СИГУР
 */
@Component
public class SigurConnectProxy {

    private static final String REQUEST_ID = "requestId";
    private final RestTemplate rest;

    @Value("${sigur.url}")
    private String sigurPathUrl;

    public SigurConnectProxy(RestTemplate rest) {
        this.rest = rest;
    }

    public void getUsers() {
        //todo: Реализовать!
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(REQUEST_ID, UUID.randomUUID().toString());
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<Void> resp = rest.exchange(sigurPathUrl, HttpMethod.GET, entity, null);
//        resp.getBody();
    }

}
