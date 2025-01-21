package org.project.market.service.product;

import java.util.List;
import org.project.market.dto.input.product.CreateProductInput;

public interface ProductService {

    Long createProduct(CreateProductInput input, List<String> imageUrls);
}
