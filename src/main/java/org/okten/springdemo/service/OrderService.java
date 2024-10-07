package org.okten.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.okten.springdemo.dto.OrderDTO;
import org.okten.springdemo.entity.Order;
import org.okten.springdemo.entity.OrderItemId;
import org.okten.springdemo.entity.Product;
import org.okten.springdemo.mapper.OrderMapper;
import org.okten.springdemo.repository.OrderRepository;
import org.okten.springdemo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public List<OrderDTO> getOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::mapToDTO)
                .toList();
    }

    public OrderDTO createOrder(OrderDTO sourceOrderDTO) {
        Order order = orderMapper.mapToEntity(sourceOrderDTO);
        order.getItems().forEach(orderItem -> {
            OrderItemId orderItemId = orderItem.getId();
            Product product = productRepository.findById(orderItemId.getProduct().getId()).orElseThrow();
            orderItemId.setProduct(product);
        });
        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapToDTO(savedOrder);
    }
}
