package org.project.market.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.market.dto.input.auth.LoginInput;
import org.project.market.dto.request.auth.LoginRequest;
import org.project.market.global.response.CommonResponse;
import org.project.market.mapper.AuthMapper;
import org.project.market.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "인증 API", description = "인증에 관한 API")
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final AuthMapper authMapper;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력 형식"),
        @ApiResponse(responseCode = "404", description = "1. 가입되지 않은 이메일 2. 비밀번호가 일치하지 않음"),
    })
    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        LoginInput input = authMapper.toInput(request);

        String token = authService.login(input);

        return ResponseEntity.status(HttpStatus.OK)
            .body(CommonResponse.success("로그인 성공", token));
    }
}
