package org.okten.springdemo.dto;

import lombok.Builder;

@Builder
public record ProductDeletedEvent(Long productId){

}
 