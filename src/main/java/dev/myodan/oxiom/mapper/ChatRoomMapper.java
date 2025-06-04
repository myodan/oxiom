package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.ChatRoom;
import dev.myodan.oxiom.dto.ChatRoomResponse;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class}
)
public interface ChatRoomMapper {

    ChatRoom toEntity(ChatRoomResponse chatRoomResponse);

    ChatRoomResponse toResponse(ChatRoom chatRoom);

}