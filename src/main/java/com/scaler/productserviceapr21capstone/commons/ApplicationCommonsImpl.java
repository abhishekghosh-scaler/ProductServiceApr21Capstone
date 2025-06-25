package com.scaler.productserviceapr21capstone.commons;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApplicationCommonsImpl implements ApplicationCommons
{
    private final RestTemplate restTemplate;

    public ApplicationCommonsImpl(@Qualifier("getLoadBalancedRestTemplate")
                                  RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @Override
    public void validateToken(String token)
    {
        if(token == null || token.isEmpty())
        {
            throw new RuntimeException("Invalid token: token is empty");
        }

        String url="http://UserServiceApr21Capstone/validate";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);

        HttpEntity<Void> responseEntity = new HttpEntity<>(headers);

        boolean isValid = restTemplate.postForObject(url, responseEntity, Boolean.class);

        if(Boolean.FALSE.equals(isValid))
        {
            throw new RuntimeException("Invalid token: token is invalid");
        }
    }
}
