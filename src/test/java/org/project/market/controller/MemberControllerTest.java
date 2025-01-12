package org.project.market.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.project.market.dto.request.member.CreateMemberRequest;
import org.project.market.entity.MemberEntity;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원가입 API")
    @Nested
    class CreateMemberTest {

        @DisplayName("성공")
        @Test
        void shouldReturnsCreated_whenSuccessful() throws Exception {
            // given
            CreateMemberRequest request = CreateMemberRequest.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            // when
            mockMvc.perform(post("/api/member/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            // then
                .andExpect(status().isCreated())
                .andDo(print());
        }

        @DisplayName("실패 - 잘못된 입력 형식")
        @Test
        void shouldReturnsBadRequest_whenInvalidRequest() throws Exception {
            // given
            CreateMemberRequest request = CreateMemberRequest.builder()
                .email("test-market")
                .password("test")
                .build();

            // when
            mockMvc.perform(post("/api/member/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value(ErrorEnum.INVALID_FORMAT.name()))
                .andDo(print());
        }

        @DisplayName("실패 - 이미 가입된 이메일")
        @Test
        void shouldReturnsBadRequest_whenEmailAlreadyExists() throws Exception {
            // given
            MemberEntity memberEntity = MemberEntity.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            memberRepository.save(memberEntity);

            CreateMemberRequest request = CreateMemberRequest.builder()
                .email("test-market@domain.com")
                .password("test-password1")
                .build();

            // when
            mockMvc.perform(post("/api/member/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value(ErrorEnum.EMAIL_ALREADY_EXISTS.name()))
                .andDo(print());
        }
    }
}