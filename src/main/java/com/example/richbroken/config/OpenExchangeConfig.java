package com.example.richbroken.config;

import com.example.richbroken.model.OpenExchangeRates;
import com.example.richbroken.utils.OpenExchangeDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenExchangeConfig {

    @Bean
    public Decoder openExchangeDecoder() {
        return new JacksonDecoder(customObjectMapper());
    }

    public ObjectMapper customObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OpenExchangeRates.class, new OpenExchangeDeserializer(OpenExchangeRates.class));
        mapper.registerModule(module);
        return mapper;
    }
}
