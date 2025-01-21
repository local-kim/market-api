package org.project.market.util;

import java.util.Optional;
import org.project.market.entity.MemberEntity;
import org.project.market.global.security.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    /**
     * 현재 인증된 회원의 정보를 Optional로 반환합니다.
     *
     * @return  현재 인증된 회원의 MemberEntity를 포함하는 Optional,
     *          인증된 회원이 없거나 유효하지 않은 경우 빈 Optional
     */
    public Optional<MemberEntity> getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetail userDetails) {
            return Optional.of(userDetails.getMemberEntity());
        }

        return Optional.empty();
    }
}
