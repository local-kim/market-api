package org.project.market.dto.input.product;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.project.market.entity.MemberEntity;
import org.project.market.entity.ProductEntity;
import org.project.market.global.enums.ProductStatusEnum;

@Getter
@Setter
@Builder
public class CreateProductInput {

    private String name;

    private String description;

    private Long price;

    private Integer quantity;

    public ProductEntity toEntity(MemberEntity sellerEntity, List<String> imageUrls) {
        return ProductEntity.builder()
            .sellerEntity(sellerEntity)
            .name(name)
            .description(description)
            .price(price)
            .status(ProductStatusEnum.ON_SALE)
            .quantity(quantity)
            .stock(quantity)
            .imageUrls(imageUrls)
            .build();
    }
}
