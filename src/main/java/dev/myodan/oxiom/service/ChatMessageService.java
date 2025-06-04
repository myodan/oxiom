package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.ChatMessage;
import dev.myodan.oxiom.dto.ChatMessageRequest;
import dev.myodan.oxiom.dto.ChatMessageResponse;
import dev.myodan.oxiom.mapper.ChatMessageMapper;
import dev.myodan.oxiom.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    public ChatMessageResponse createChatMessage(Long roomId, Long senderId, ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageRequest);
        chatMessage.setRoomId(roomId);
        chatMessage.setSenderId(senderId);

        return chatMessageMapper.toResponse(chatMessageRepository.save(chatMessage));
    }

}
