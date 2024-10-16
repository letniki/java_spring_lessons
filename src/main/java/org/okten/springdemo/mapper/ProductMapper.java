package org.okten.springdemo.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.okten.springdemo.api.dto.ProductDto;
import org.okten.springdemo.entity.Product;

@Mapper
public interface ProductMapper {

    Product mapToEntity(ProductDto productDto);

    ProductDto mapToDto(Product product);

    Product updateEntity(@MappingTarget Product product, ProductDto productDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateEntityPartially(@MappingTarget Product product, ProductDto productDto);
}
