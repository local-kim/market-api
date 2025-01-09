package org.project.market.mapper;

import org.mapstruct.Mapper;
import org.project.market.dto.input.member.CreateMemberInput;
import org.project.market.dto.request.member.CreateMemberRequest;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    CreateMemberInput toInput(CreateMemberRequest createMemberRequest);

}
