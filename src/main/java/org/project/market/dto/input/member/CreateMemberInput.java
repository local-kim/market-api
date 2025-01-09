package org.project.market.dto.input.member;

import lombok.Getter;
import lombok.Setter;
import org.project.market.entity.MemberEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class CreateMemberInput {

    private String email;

    private String password;

    public MemberEntity toMemberEntity(PasswordEncoder passwordEncoder) {
        return MemberEntity.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .build();
    }

}
