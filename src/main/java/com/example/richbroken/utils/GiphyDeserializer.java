package com.example.richbroken.utils;

import com.example.richbroken.model.Giphy;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GiphyDeserializer extends StdDeserializer<Giphy> {
    public static final Logger logger = LoggerFactory.getLogger(GiphyDeserializer.class);


    public GiphyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Giphy deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        List<String> urls = new ArrayList<>();

        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        JsonNode arrNode = node.findPath("data");

        if (arrNode.isArray()) {
            for (JsonNode n : arrNode) {
                JsonNode imagesNode = n.findPath("images");
                JsonNode value = imagesNode.findPath("original").get("url");
                if (value != null) {
                    urls.add(value.asText());
                }
            }
        }
//        logger.info("Giphy URLs:" + urls);
        return new Giphy(urls);
    }
}
