package ru.mentee.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class MessageConverterConfig {
    @Bean
    public MappingJackson2HttpMessageConverter jsonMessageConverter() {
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }


//    @Bean
//    public MappingJackson2XmlHttpMessageConverter xmlMessageConverter() {
//        MappingJackson2XmlHttpMessageConverter converter =
//                new MappingJackson2XmlHttpMessageConverter();
//        converter.setDefaultCharset(StandardCharsets.UTF_8);
//        return converter;
//    }
}
