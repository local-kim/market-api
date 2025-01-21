package org.project.market.service.auth;

import org.project.market.dto.input.auth.LoginInput;

public interface AuthService {

    String login(LoginInput input);
}
