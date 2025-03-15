package com.example.OrderService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {
    private Integer id;
    @NotNull
    @JsonProperty("personId")
    private Integer personId;

    @NotEmpty(message = "Address should not be empty")
    private String shippingAddress;
}
