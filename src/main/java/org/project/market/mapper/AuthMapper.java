package org.project.market.mapper;

import org.mapstruct.Mapper;
import org.project.market.dto.input.auth.LoginInput;
import org.project.market.dto.request.auth.LoginRequest;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    LoginInput toInput(LoginRequest request);
}
