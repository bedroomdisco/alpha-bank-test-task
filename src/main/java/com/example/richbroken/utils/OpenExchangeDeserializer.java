package com.example.richbroken.utils;

import com.example.richbroken.model.OpenExchangeRates;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class OpenExchangeDeserializer extends StdDeserializer<OpenExchangeRates> {
    public static final Logger logger = LoggerFactory.getLogger(OpenExchangeDeserializer.class);

    public OpenExchangeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OpenExchangeRates deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        JsonNode arrNode = node.findPath("rates");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Float> result = mapper.convertValue(arrNode, new TypeReference<>() {
        });
//        logger.info("OpenExchange rates:" + result);
        return new OpenExchangeRates(result);
    }


}
