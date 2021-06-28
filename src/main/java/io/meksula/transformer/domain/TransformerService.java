package io.meksula.transformer.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class TransformerService {

    private static ObjectMapper yamlObjectMapper = new YAMLMapper();
    private static XmlMapper xmlObjectMapper = new XmlMapper();
    private static ObjectMapper jsonObjectMapper = new JsonMapper();

    private static final Map<Key, Function<byte[], String>> functions = new HashMap<>() {{
        put(Key.of(TransformerTypes.YAML, TransformerTypes.XML),
                data -> {
                    try {
                        Object in = yamlObjectMapper.readValue(data, Object.class);
                        return xmlObjectMapper.writeValueAsString(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "";
                    }
                });
        put(Key.of(TransformerTypes.YAML, TransformerTypes.JSON),
                data -> {
                    try {
                        Object in = yamlObjectMapper.readValue(data, Object.class);
                        return jsonObjectMapper.writeValueAsString(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "";
                    }
                });
        put(Key.of(TransformerTypes.XML, TransformerTypes.YAML),
                data -> {
                    try {
                        Object in = xmlObjectMapper.readValue(data, Object.class);
                        return yamlObjectMapper.writeValueAsString(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "";
                    }
                });
        put(Key.of(TransformerTypes.XML, TransformerTypes.JSON),
                data -> {
                    try {
                        Object in = xmlObjectMapper.readValue(data, Object.class);
                        return jsonObjectMapper.writeValueAsString(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "";
                    }
                });
        put(Key.of(TransformerTypes.JSON, TransformerTypes.YAML),
                data -> {
                    try {
                        Object in = jsonObjectMapper.readValue(data, Object.class);
                        return yamlObjectMapper.writeValueAsString(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "";
                    }
                });
        put(Key.of(TransformerTypes.JSON, TransformerTypes.XML),
                data -> {
                    try {
                        Object in = jsonObjectMapper.readValue(data, Object.class);
                        return xmlObjectMapper.writeValueAsString(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "";
                    }
                });
    }};

    public TransformerDto transform(TransformerDto transformerDto) {
        log.info("Request for transformation from: {}, to: {}", transformerDto.getFrom(), transformerDto.getTo());
        Key key = new Key(transformerDto.getFrom(), transformerDto.getTo());
        byte[] plain = Base64.getDecoder().decode(transformerDto.getEncodedData().getBytes(StandardCharsets.UTF_8));
        String result = functions.get(key).apply(plain);
        byte[] resultEncoded = Base64.getEncoder().encode(result.getBytes(StandardCharsets.UTF_8));
        return new TransformerDto(transformerDto.getFrom(), transformerDto.getTo(), new String(resultEncoded));
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    static class Key {
        private TransformerTypes from;
        private TransformerTypes to;
    }
}
