package com.mj.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/*配置错误。。。待继续。现在改用okhttp*/
@Configuration
public class AsyncRestTemplateConfig {
    @Bean
    public AsyncRestTemplate restTemplate(AsyncClientHttpRequestFactory factory){
        return new AsyncRestTemplate(factory);
    }

    @Bean  
    public AsyncClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//ms  
        factory.setConnectTimeout(15000);//ms  
        return factory;  
    }  
}  