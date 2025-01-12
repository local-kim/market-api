package org.project.market.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {

    @NotBlank
    @Email
    @Schema(description = "이메일", example = "aaaa@bbbb.cc")
    private String email;

    @NotBlank
    @Schema(description = "비밀번호", example = "password1!")
    private String password;

}
