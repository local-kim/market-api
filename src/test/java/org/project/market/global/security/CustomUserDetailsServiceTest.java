package org.project.market.global.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.market.entity.MemberEntity;
import org.project.market.global.exception.CustomException;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Mock
    MemberRepository memberRepository;

    @DisplayName("이메일로 회원 조회하는 메서드")
    @Nested
    class LoadUserByUsernameTest {

        @DisplayName("성공")
        @Test
        void shouldReturnUserDetails_whenSuccessful() {
            // given
            String email = "test-market@domain.com";
            String password = "test-password1";

            MemberEntity memberEntity = MemberEntity.builder()
                    .email(email)
                    .password(password)
                    .build();

            when(memberRepository.findByEmail(email))
                .thenReturn(Optional.of(memberEntity));

            // when
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // then
            assertEquals(email, userDetails.getUsername());
        }

        @DisplayName("일치하는 이메일이 존재하지 않으면 예외를 던짐")
        @Test
        void shouldThrowException_whenEmailNotFound() {
            // given
            String email = "test-market@domain.com";

            when(memberRepository.findByEmail(email))
                .thenReturn(Optional.empty());

            // when
            CustomException exception = assertThrows(CustomException.class,
                () -> customUserDetailsService.loadUserByUsername(email));

            // then
            assertEquals(ErrorEnum.USER_NOT_FOUND, exception.getErrorEnum());
        }
    }
}