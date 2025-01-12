package org.project.market.dto.input.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginInput {

    private String email;

    private String password;

}
