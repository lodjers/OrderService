package com.example.OrderService.services;

import com.example.OrderService.DTO.OrderDTO;
import com.example.OrderService.models.Order;
import com.example.OrderService.repositories.OrderRepository;
import com.example.OrderService.util.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
           throw new OrderNotFoundException("Заказ не найден");
        }
    }
}
