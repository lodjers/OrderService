package com.example.OrderService.services;
import static org.mockito.Mockito.*;
import com.example.OrderService.models.Order;
import com.example.OrderService.repositories.OrderRepository;
import com.example.OrderService.util.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;
    @Test
    public void registerOneTime() {
        Order order = new Order(1, 1, "Perm");
        orderService.register(order);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
    }
    @Test
    void getOrderById() {
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(50));
    }
}