package org.project.market.dto.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateMemberRequest {

    @NotBlank
    @Email
    @Schema(description = "이메일", example = "aaaa@bbbb.cc")
    private String email;

    @NotBlank
    @Size(min = 8, max = 16)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[~!@#$%^&*\\-_+=;:,.?]).*$", message = "영문, 숫자, 특수문자를 1개 이상 포함해야합니다")
    @Schema(description = "비밀번호", example = "password1!")
    private String password;

}
