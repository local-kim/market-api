package org.project.market.service.member;

import lombok.RequiredArgsConstructor;
import org.project.market.dto.input.member.CreateMemberInput;
import org.project.market.entity.MemberEntity;
import org.project.market.global.exception.CustomException;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createMember(CreateMemberInput input) {
        // 이메일 중복 체크
        if(memberRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new CustomException(ErrorEnum.EMAIL_ALREADY_EXISTS);
        }

        MemberEntity memberEntity = input.toMemberEntity(passwordEncoder);

        memberRepository.save(memberEntity);
    }
}
