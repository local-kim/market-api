package org.project.market.service;

import lombok.RequiredArgsConstructor;
import org.project.market.dto.input.member.CreateMemberInput;
import org.project.market.entity.MemberEntity;
import org.project.market.global.exception.CustomException;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createMember(CreateMemberInput createMemberInput) {
        // 이메일 중복 체크
        if(memberRepository.findByEmail(createMemberInput.getEmail()).isPresent()) {
            throw new CustomException(ErrorEnum.EMAIL_ALREADY_EXISTS, null);
        }

        MemberEntity memberEntity = createMemberInput.toMemberEntity(passwordEncoder);

        memberRepository.save(memberEntity);
    }

}
