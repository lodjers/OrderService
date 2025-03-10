package com.example.OrderService.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.OrderService.DTO.OrderDTO;
import com.example.OrderService.models.Order;
import com.example.OrderService.services.OrderService;
import com.example.OrderService.services.UserServiceClient;
import com.example.OrderService.util.ErrorsUtil;
import com.example.OrderService.util.OrderNotCreatedException;
import com.example.OrderService.util.OrderNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @Mock
    private OrderService orderService;
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }
    @Test
    void getOrders() throws Exception {
        mockMvc.perform(get("/orders")).andExpect(status().isOk());
        verify(orderService,times(1)).getOrders();
    }
    @Test
    void orderRegistration() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPersonId(1);
        orderDTO.setShippingAddress("Test address");

        String orderJson = objectMapper.writeValueAsString(orderDTO);
        mockMvc.perform(post("/orders/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isCreated());
        verify(modelMapper, times(2)).map(orderDTO, Order.class);
        verify(userServiceClient, times(1)).findByUserId(orderDTO.getPersonId());
        verify(orderService, times(1)).register(modelMapper.map(orderDTO, Order.class));
    }
    @Test
    void getOrderById() throws Exception {

        Order order = new Order();
        order.setId(1);
        order.setPersonId(1);
        order.setShippingAddress("Test address");

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);
        orderDTO.setPersonId(1);
        orderDTO.setShippingAddress("Test address");

        when(orderService.getOrderById(1)).thenReturn(order);
        when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO);

        System.out.println(orderService.getOrderById(1));
        mockMvc.perform(get("/orders/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").value(orderDTO.getPersonId()))
                .andExpect(jsonPath("$.shippingAddress").value(orderDTO.getShippingAddress()));
        verify(orderService, times(2)).getOrderById(1);
    }
}