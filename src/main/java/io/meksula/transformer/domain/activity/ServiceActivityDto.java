package io.meksula.transformer.domain.activity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ServiceActivityDto {
    private Long id;
    private long accessTimestamp;
    private String clientIp;
    private String requestUri;
}
