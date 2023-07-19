package com.trellolike.util;

import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class ApiCaller {

    private final Loader loader;

    public ApiCaller() {
        this.loader = new Loader();
    }

    public String callApi(String body, String path, HttpMethod httpMethod) throws URISyntaxException {
        URI uri = new URI("http", null, "localhost", 8080, path, null, null);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(body, httpHeaders);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);

        try {
            ResponseEntity<String> serverResponse = restTemplate.exchange(uri, httpMethod, requestEntity, String.class);
            return serverResponse.getBody();
        } catch (HttpStatusCodeException e) {
            loader.InternalError(e);
            return null;
        }
    }
}
