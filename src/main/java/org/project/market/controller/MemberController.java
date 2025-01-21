package org.project.market.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.market.dto.input.member.CreateMemberInput;
import org.project.market.dto.request.member.CreateMemberRequest;
import org.project.market.global.response.CommonResponse;
import org.project.market.mapper.MemberMapper;
import org.project.market.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원에 관한 API")
@RequestMapping("/api/member")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = "1. 잘못된 입력 형식 2. 이미 가입된 이메일"),
    })
    @Operation(summary = "회원가입", description = "비밀번호: 8~16자의 영문, 숫자, 특수문자를 1개 이상 포함한 문자열")
    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@Valid @RequestBody CreateMemberRequest request) {
        CreateMemberInput input = memberMapper.toInput(request);

        memberService.createMember(input);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CommonResponse.success("회원가입 성공"));
    }
}
