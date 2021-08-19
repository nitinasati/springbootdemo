package com.asatisamaj.matrimony.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.asatisamaj.matrimony.domain.MatrimonyResponse;
import com.asatisamaj.matrimony.utils.HelperUtils;

@Service
public class GenericService {


    @Autowired
    @Qualifier("restTemplateBean")
    private RestTemplate restTemplate;

    public ResponseEntity<?> retriveSearchResult(String serviceUrl,Object object) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestMap = new HttpEntity<>(object, header);
        HelperUtils.jacksonConverter(restTemplate);
        return restTemplate.exchange(serviceUrl, HttpMethod.POST, requestMap, MatrimonyResponse.class);
    }
}
