package org.project.market.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.market.dto.input.auth.LoginInput;
import org.project.market.entity.MemberEntity;
import org.project.market.global.exception.CustomException;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.global.security.CustomUserDetail;
import org.project.market.global.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @DisplayName("로그인 메서드")
    @Nested
    class LoginTest {

        @DisplayName("로그인 성공하면 JWT 토큰을 반환함")
        @Test
        void shouldReturnJwtToken_whenSuccessful() {
            // given
            String expectedToken = "mock-token";

            LoginInput input = LoginInput.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            UserDetails userDetails = new CustomUserDetail(MemberEntity.builder()
                .email("test-market@domain.com")
                .password("encoded-password1")
                .build());

            when(userDetailsService.loadUserByUsername(input.getEmail()))
                .thenReturn(userDetails);

            when(passwordEncoder.matches(input.getPassword(), userDetails.getPassword()))
                .thenReturn(true);

            when(jwtUtil.generateToken(userDetails.getUsername()))
                .thenReturn(expectedToken);

            // when
            String actualToken = authService.login(input);

            // then
            assertEquals(expectedToken, actualToken);
        }

        @DisplayName("가입되지 않은 이메일로 로그인하면 예외를 던짐")
        @Test
        void shouldThrowException_whenEmailDoesNotExist() {
            // given
            LoginInput input = LoginInput.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            when(userDetailsService.loadUserByUsername(input.getEmail()))
                .thenThrow(new CustomException(ErrorEnum.USER_NOT_FOUND));

            // when
            CustomException exception = assertThrows(CustomException.class, () -> authService.login(input));

            // then
            assertEquals(ErrorEnum.USER_NOT_FOUND, exception.getErrorEnum());
        }

        @DisplayName("비밀번호가 일치하지 않으면 예외를 던짐")
        @Test
        void shouldThrowException_whenPasswordDoesNotMatch() {
            // given
            LoginInput input = LoginInput.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            UserDetails userDetails = new CustomUserDetail(MemberEntity.builder()
                .email("test-market@domain.com")
                .password("encoded-password1")
                .build());

            when(userDetailsService.loadUserByUsername(input.getEmail()))
                .thenReturn(userDetails);

            when(passwordEncoder.matches(input.getPassword(), userDetails.getPassword()))
                .thenReturn(false);

            // when
            CustomException exception = assertThrows(CustomException.class, () -> authService.login(input));

            // then
            assertEquals(ErrorEnum.USER_NOT_FOUND, exception.getErrorEnum());
        }
    }
}