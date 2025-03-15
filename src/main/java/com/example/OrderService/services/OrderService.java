package com.example.OrderService.services;

import com.example.OrderService.models.Order;
import com.example.OrderService.repositories.OrderRepository;
import com.example.OrderService.errors.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    @Transactional
    public void register(Order order) {
        orderRepository.save(order);
    }
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
    public Order getOrderById(Integer id) {
        if (orderRepository.findById(id).isPresent()) {
            return orderRepository.findById(id).get();
        } else {
           throw new OrderNotFoundException();
        }
    }
}
