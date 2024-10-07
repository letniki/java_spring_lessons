package org.okten.springdemo.mapper;

import lombok.RequiredArgsConstructor;
import org.okten.springdemo.dto.OrderDTO;
import org.okten.springdemo.dto.OrderItemDTO;
import org.okten.springdemo.entity.Order;
import org.okten.springdemo.entity.OrderItem;
import org.okten.springdemo.entity.OrderItemId;
import org.okten.springdemo.entity.Product;
import org.okten.springdemo.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public Order mapToEntity(OrderDTO dto) {
        Order order = new Order();

        order.setItems(dto.getItems()
                .stream()
                .map(orderItemDTO -> {
                    OrderItem item = new OrderItem();

                    OrderItemId orderItemId = new OrderItemId();
                    orderItemId.setOrder(order);
                    Product product = new Product();
                    product.setId(orderItemDTO.getProductId());
                    orderItemId.setProduct(product);

                    item.setId(orderItemId);

                    item.setQuantity(orderItemDTO.getQuantity());
                    item.setComment(orderItemDTO.getComment());

                    return item;
                })
                .toList());

        return order;
    }

    public OrderDTO mapToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setItems(order.getItems().stream()
                .map(this::mapToOrderItemDTO)
                .toList());
        return dto;
    }

    public OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(orderItem.getId().getProduct().getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setComment(orderItem.getComment());
        return dto;
    }
}
