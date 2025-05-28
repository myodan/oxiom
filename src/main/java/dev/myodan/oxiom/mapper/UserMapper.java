package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.dto.UserRequest;
import dev.myodan.oxiom.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper {

    User toEntity(UserResponse userResponse);

    User toEntity(UserRequest userRequest);

    UserResponse toResponse(User user);

}