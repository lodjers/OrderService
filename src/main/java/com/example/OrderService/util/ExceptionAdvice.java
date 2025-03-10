package com.example.OrderService.util;

import com.example.OrderService.errors.OrderNotCreatedException;
import com.example.OrderService.errors.OrderNotFoundException;
import com.example.OrderService.errors.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(OrderNotCreatedException.class)
    public ResponseEntity<ResponseOfError> handleOrderNotCreatedException(OrderNotCreatedException ex) {
        ResponseOfError responseOfError = new ResponseOfError("Заказ не может быть создан, неправильно введеные поля",
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseOfError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ResponseOfError> handleOrderNotFoundException(OrderNotFoundException ex) {
        ResponseOfError responseOfError = new ResponseOfError("Заказ не найден", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseOfError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseOfError> handleUserNotFoundException(UserNotFoundException ex) {
        ResponseOfError responseOfError = new ResponseOfError("Человек с указанным id не найден",
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseOfError, HttpStatus.NOT_FOUND);
    }
}
