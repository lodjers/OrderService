package com.example.OrderService.services;

import com.example.OrderService.DTO.UserDTO;
import com.example.OrderService.errors.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceClient {
    private final RestTemplate restTemplate;
    @Value("${local.host.person.service}")
    private String hostName;

    public UserDTO findByUserId(Integer id) {
        String url = hostName + "/users/" + id;
        try {
            return restTemplate.getForObject(url, UserDTO.class);
        } catch (HttpClientErrorException e) {
            throw new UserNotFoundException();
        }
    }
}
