package com.example.OrderService.config;

import com.example.OrderService.DTO.OrderDTO;
import com.example.OrderService.models.Order;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.typeMap(Order.class, OrderDTO.class)
                .addMappings(mapper -> mapper.map(Order::getPersonId, OrderDTO::setPersonId));
        return modelMapper;
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
