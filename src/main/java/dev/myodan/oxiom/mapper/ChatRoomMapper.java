package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.ChatMessage;
import dev.myodan.oxiom.domain.ChatRoom;
import dev.myodan.oxiom.dto.ChatRoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, ChatMessageMapper.class}
)
public interface ChatRoomMapper {

    ChatRoom toEntity(ChatRoomResponse chatRoomResponse);

    ChatRoomResponse toResponse(ChatRoom chatRoom);

    @Mapping(target = "id", source = "chatRoom.id")
    @Mapping(target = "createdDate", source = "chatRoom.createdDate")
    ChatRoomResponse toResponse(ChatRoom chatRoom, ChatMessage lastChatMessage);

}