package org.project.market.dto.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProductRequest {

    @NotBlank
    @Schema(description = "상품명", example = "MacBook Pro 14")
    private String name;

    @NotBlank
    @Schema(description = "설명", example = "Apple M2 Pro 칩, 32GB 메모리, 스페이스 그레이")
    private String description;

    @NotNull
    @Schema(description = "가격", example = "3330000")
    private Long price;

    @NotNull
    @Schema(description = "수량", example = "5")
    private Integer quantity;
}
