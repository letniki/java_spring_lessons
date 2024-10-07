package org.okten.springdemo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private Long id;

    @NotEmpty
    private List<@Valid OrderItemDTO> items;
}
