package io.meksula.transformer.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TransformerController {

    private TransformerService transformerService;

    @PostMapping("/transformation")
    public TransformerDto transform(@RequestBody TransformerDto transformerDto) throws UnknownHostException, JsonProcessingException {
        return transformerService.transform(transformerDto);
    }
}
