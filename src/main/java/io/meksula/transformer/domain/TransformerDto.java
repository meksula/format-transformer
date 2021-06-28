package io.meksula.transformer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransformerDto {
    private TransformerTypes from;
    private TransformerTypes to;
    private String encodedData;
}
