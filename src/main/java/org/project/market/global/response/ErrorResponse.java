package org.project.market.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    // 에러 코드
    private String code;

    // 에러 메세지
    private String message;

}