package org.project.market.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.market.dto.input.member.CreateMemberInput;
import org.project.market.entity.MemberEntity;
import org.project.market.global.exception.CustomException;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.repository.MemberRepository;
import org.project.market.service.member.MemberServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    MemberRepository memberRepository;

    @Spy
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("회원가입 메서드")
    @Nested
    class CreateMemberTest {

        @DisplayName("회원가입 성공")
        @Test
        void shouldCreateMember_whenSuccessful() {
            // given
            CreateMemberInput input = CreateMemberInput.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            when(memberRepository.findByEmail(input.getEmail()))
                .thenReturn(Optional.empty());

            when(memberRepository.save(any(MemberEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

            // when
            memberService.createMember(input);

            // then
            verify(memberRepository).save(any(MemberEntity.class));
        }

        @DisplayName("이미 가입된 이메일이면 예외를 던짐")
        @Test
        void shouldThrowException_whenEmailAlreadyExists() {
            // given
            CreateMemberInput input = CreateMemberInput.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            MemberEntity memberEntity = MemberEntity.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            when(memberRepository.findByEmail("test-market@domain.com"))
                .thenReturn(Optional.of(memberEntity));

            // when
            CustomException exception = assertThrows(CustomException.class,
                () -> memberService.createMember(input));

            // then
            assertEquals(ErrorEnum.EMAIL_ALREADY_EXISTS, exception.getErrorEnum());
            verify(memberRepository, never()).save(any(MemberEntity.class));
        }
    }
}