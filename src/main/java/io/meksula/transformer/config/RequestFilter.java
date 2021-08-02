package io.meksula.transformer.config;

import io.meksula.transformer.domain.activity.ServiceActivityEntity;
import io.meksula.transformer.domain.activity.ServiceActivityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
@Order(1)
@AllArgsConstructor
public class RequestFilter implements Filter {

    private ServiceActivityRepository serviceActivityRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        log.info("Just handled request to format-transformer");
        serviceActivityRepository.save(ServiceActivityEntity.builder()
                                                            .accessTimestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond())
                                                            .requestUri(request.getRequestURI())
                                                            .clientIp(request.getRemoteHost())
                                                            .build());

        filterChain.doFilter(servletRequest, servletResponse);
    }
}