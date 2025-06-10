package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.ChatMessage;
import dev.myodan.oxiom.domain.ChatRoom;
import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.dto.ChatRoomResponse;
import dev.myodan.oxiom.mapper.ChatRoomMapper;
import dev.myodan.oxiom.repository.ChatMessageRepository;
import dev.myodan.oxiom.repository.ChatRoomRepository;
import dev.myodan.oxiom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional(readOnly = true)
    public Page<ChatRoomResponse> getChatRoomsByUserId(Long userId, Pageable pageable) {
        Page<ChatRoom> chatRooms = chatRoomRepository.findByUser1IdOrUser2Id(userId, userId, pageable);

        if (!chatRooms.hasContent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "참여하고있는 채팅방이 없습니다.");
        }

        List<Long> chatRoomIds = chatRooms
                .getContent()
                .stream()
                .map(ChatRoom::getId)
                .toList();

        List<ChatMessage> latestMessages = chatMessageRepository.findLatestMessagesByChatRoomIds(chatRoomIds);

        Map<Long, ChatMessage> chatRoomIdToLatestMessage = latestMessages
                .stream()
                .collect(Collectors.toMap(ChatMessage::getChatRoomId, Function.identity()));

        return chatRooms.map((chatRoom) -> chatRoomMapper.toResponse(chatRoom, chatRoomIdToLatestMessage.get(chatRoom.getId())));
    }

    @Transactional(readOnly = true)
    public ChatRoomResponse getChatRoomById(Long id, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        ChatMessage lastChatMessage = chatMessageRepository.findFirstByChatRoomIdOrderByCreatedDateDesc(chatRoom.getId());

        if (!chatRoom.getUser1().getId().equals(userId) && !chatRoom.getUser2().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return chatRoomMapper.toResponse(chatRoom, lastChatMessage);
    }

    @Transactional
    public ChatRoomResponse createChatRoom(Long user1Id, Long user2Id) {
        List<User> users = userRepository.findAllByIdInOrderById(List.of(user1Id, user2Id));

        if (users.size() != 2) {
            throw new IllegalArgumentException("사용자 중 한 명 이상이 존재하지 않습니다.");
        }

        if (chatRoomRepository.existsByUser1AndUser2(users.getFirst(), users.getLast())) {
            throw new IllegalArgumentException("이미 동일한 채팅방이 존재합니다.");
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .user1(users.getFirst())
                .user2(users.getLast())
                .build();

        return chatRoomMapper.toResponse(chatRoomRepository.save(chatRoom));
    }
}
