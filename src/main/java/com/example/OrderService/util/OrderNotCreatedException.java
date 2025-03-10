package com.example.OrderService.util;

public class OrderNotCreatedException extends RuntimeException{
    public OrderNotCreatedException(String msg) {
        super(msg);
    }
}
