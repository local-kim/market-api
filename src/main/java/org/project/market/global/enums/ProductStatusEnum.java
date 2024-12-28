package org.project.market.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatusEnum {

    ON_SALE("판매중"),
    RESERVED("예약중"),
    SOLD_OUT("완료"),
    ;

    private final String value;

}
