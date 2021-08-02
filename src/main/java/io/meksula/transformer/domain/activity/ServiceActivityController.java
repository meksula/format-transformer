package io.meksula.transformer.domain.activity;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/activity")
public class ServiceActivityController {

    private ServiceActivityRepository serviceActivityRepository;

    @GetMapping
    List<ServiceActivityDto> findAllActivities() {
        final List<ServiceActivityDto> serviceActivityDtoList = new ArrayList<>();
        Iterator<ServiceActivityEntity> iterator = serviceActivityRepository.findAll().iterator();
        while (iterator.hasNext()) {
            ServiceActivityEntity next = iterator.next();
            serviceActivityDtoList.add(ServiceActivityDto.of(next.getId(), next.getAccessTimestamp(), next.getClientIp(), next.getRequestUri()));
        }
        return serviceActivityDtoList;
    }

}
