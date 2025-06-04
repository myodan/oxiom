package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.ChatMessage;
import dev.myodan.oxiom.dto.ChatMessageRequest;
import dev.myodan.oxiom.dto.ChatMessageResponse;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ChatMessageMapper {

    ChatMessage toEntity(ChatMessageResponse chatMessageResponse);

    ChatMessage toEntity(ChatMessageRequest chatMessageRequest);

    ChatMessageResponse toResponse(ChatMessage chatMessage);

}