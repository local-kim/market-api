package org.project.market.mapper;

import org.mapstruct.Mapper;
import org.project.market.dto.input.member.SignUpInput;
import org.project.market.dto.request.member.SignUpRequest;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    SignUpInput toInput(SignUpRequest signUpRequest);

}
