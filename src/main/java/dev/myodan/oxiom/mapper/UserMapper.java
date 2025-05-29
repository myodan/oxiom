package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.dto.UserCreateRequest;
import dev.myodan.oxiom.dto.UserResponse;
import dev.myodan.oxiom.dto.UserUpdateRequest;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper {

    User toEntity(UserResponse userResponse);

    User toEntity(UserCreateRequest userCreateRequest);

    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserUpdateRequest userResponse, @MappingTarget User user);

}