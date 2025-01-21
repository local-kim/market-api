package org.project.market.mapper;

import org.mapstruct.Mapper;
import org.project.market.dto.input.product.CreateProductInput;
import org.project.market.dto.request.product.CreateProductRequest;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    CreateProductInput toInput(CreateProductRequest request);
}
