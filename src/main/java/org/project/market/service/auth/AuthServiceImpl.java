package org.project.market.service.auth;

import lombok.RequiredArgsConstructor;
import org.project.market.dto.input.auth.LoginInput;
import org.project.market.global.exception.CustomException;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.util.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginInput input) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(input.getEmail());

        if(!passwordEncoder.matches(input.getPassword(), userDetails.getPassword())) {
            throw new CustomException(ErrorEnum.USER_NOT_FOUND);
        }

        return jwtUtil.generateToken(userDetails.getUsername());
    }
}
