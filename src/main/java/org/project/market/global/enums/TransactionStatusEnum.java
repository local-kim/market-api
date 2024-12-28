package org.project.market.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatusEnum {

    IN_PROGRESS("거래중"),
    CONFIRMED("판매승인"),
    COMPLETED("구매확정"),
    ;

    private final String value;

}
