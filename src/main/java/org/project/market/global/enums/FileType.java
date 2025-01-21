package org.project.market.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {

    PRODUCT_IMAGE("product/"),
    ;

    private final String folder;
}
