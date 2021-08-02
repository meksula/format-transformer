package io.meksula.transformer.domain.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("service_activity")
public class ServiceActivityEntity {

    @Id
    private Long id;
    private long accessTimestamp;
    private String clientIp;
    private String requestUri;
}
