package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.ChatMessage;
import dev.myodan.oxiom.domain.ChatRoom;
import dev.myodan.oxiom.dto.ChatMessageRequest;
import dev.myodan.oxiom.dto.ChatMessageResponse;
import dev.myodan.oxiom.mapper.ChatMessageMapper;
import dev.myodan.oxiom.repository.ChatMessageRepository;
import dev.myodan.oxiom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatRoomRepository chatRoomRepository;

    public Page<ChatMessageResponse> getChatMessagesByChatRoomId(Long chatRoomId, Pageable pageable, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "채팅방이 존재하지 않습니다.")
        );

        if (!chatRoom.getUser1().getId().equals(userId) && !chatRoom.getUser2().getId().equals(userId)) {
            throw new AccessDeniedException("해당 채팅방에 참여한 유저가 아닙니다.");
        }

        return chatMessageRepository.findAllByChatRoomId(chatRoomId, pageable).map(chatMessageMapper::toResponse);
    }

    public ChatMessageResponse createChatMessage(Long chatRoomId, ChatMessageRequest chatMessageRequest, Long userId) {
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageRequest);
        chatMessage.setChatRoomId(chatRoomId);
        chatMessage.setSenderId(userId);

        return chatMessageMapper.toResponse(chatMessageRepository.save(chatMessage));
    }

}
