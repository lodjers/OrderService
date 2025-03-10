package com.example.OrderService.controllers;

import com.example.OrderService.DTO.OrderDTO;
import com.example.OrderService.models.Order;
import com.example.OrderService.services.OrderService;
import com.example.OrderService.services.UserServiceClient;
import com.example.OrderService.util.ErrorsUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserServiceClient userServiceClient;
    private final ModelMapper modelMapper;

    @PostMapping("/registration")
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderDTO orderDTO, BindingResult bindingResult) {
       Order order = modelMapper.map(orderDTO, Order.class);
        if (bindingResult.hasErrors()) {
            ErrorsUtil.returnErrorsToClient(bindingResult);
        }
        userServiceClient.findByUserId(orderDTO.getPersonId());
        orderService.register(modelMapper.map(orderDTO, Order.class));
        return ResponseEntity.status(HttpStatus.CREATED).body("Заказ зарегистрирован");
    }
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getOrders().stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                        .toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }
}
