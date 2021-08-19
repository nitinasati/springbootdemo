package com.asatisamaj.matrimony.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HelperUtils {

    private HelperUtils(){}
    public static void jacksonConverter(RestTemplate restTemplate){
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> myMediaTypes = new ArrayList<>(mappingJackson2HttpMessageConverter.getSupportedMediaTypes());
        myMediaTypes.add (MediaType.parseMediaType ("text/javascript; charset=utf-8"));
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(myMediaTypes);
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
    }
}