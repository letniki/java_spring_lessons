package org.okten.springdemo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okten.springdemo.dto.OrderDTO;
import org.okten.springdemo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }
}
