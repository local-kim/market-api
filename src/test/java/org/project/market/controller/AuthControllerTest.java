package org.project.market.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.project.market.dto.request.auth.LoginRequest;
import org.project.market.entity.MemberEntity;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @DisplayName("로그인 API")
    @Nested
    class LoginTest {

        @DisplayName("성공")
        @Test
        void shouldReturnOk_whenSuccessful() throws Exception {
            // given
            MemberEntity memberEntity = MemberEntity.builder()
                .email("test-market@domain.com")
                .password(passwordEncoder.encode("test-password1"))
                .build();

            memberRepository.save(memberEntity);

            LoginRequest request = LoginRequest.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            // when
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isString())
                .andDo(print());
        }

        @DisplayName("실패 - 가입되지 않은 이메일")
        @Test
        void shouldReturnNotFound_whenEmailDoesNotExist() throws Exception {
            // given
            LoginRequest request = LoginRequest.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            // when
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            // then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value(ErrorEnum.USER_NOT_FOUND.name()))
                .andDo(print());
        }

        @DisplayName("실패 - 비밀번호가 일치하지 않음")
        @Test
        void shouldReturnNotFound_whenPasswordDoesNotMatch() throws Exception {
            // given
            MemberEntity memberEntity = MemberEntity.builder()
                .email("test-market@domain.com")
                .password(passwordEncoder.encode("test-password1"))
                .build();

            memberRepository.save(memberEntity);

            LoginRequest request = LoginRequest.builder()
                .email("test-market@domain.com")
                .password("wrong-password")
                .build();

            // when
            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            // then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value(ErrorEnum.USER_NOT_FOUND.name()))
                .andDo(print());
        }
    }
}