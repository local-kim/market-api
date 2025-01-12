package org.project.market.global.security;

import lombok.RequiredArgsConstructor;
import org.project.market.entity.MemberEntity;
import org.project.market.global.exception.CustomException;
import org.project.market.global.exception.ErrorEnum;
import org.project.market.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(username)
            .orElseThrow(() -> new CustomException(ErrorEnum.USER_NOT_FOUND));

        return new CustomUserDetail(memberEntity);
    }
}
