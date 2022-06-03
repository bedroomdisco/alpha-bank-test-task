package com.example.richbroken.config;

import com.example.richbroken.model.Giphy;
import com.example.richbroken.utils.GiphyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GiphyFeignConfig {

    @Bean
    public Decoder giphyDecoder() {
        return new JacksonDecoder(customObjectMapper());
    }

    public ObjectMapper customObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Giphy.class, new GiphyDeserializer(Giphy.class));
        mapper.registerModule(module);
        return mapper;
    }
}
