package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.ChatRoom;
import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.dto.ChatRoomResponse;
import dev.myodan.oxiom.mapper.ChatRoomMapper;
import dev.myodan.oxiom.repository.ChatRoomRepository;
import dev.myodan.oxiom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<ChatRoomResponse> getChatRoomsByUserId(Long id, Pageable pageable) {
        return chatRoomRepository.findByUser1IdOrUser2Id(id, id, pageable).map(chatRoomMapper::toResponse);
    }

    @Transactional
    public ChatRoomResponse createChatRoom(Long user1Id, Long user2Id) {
        List<User> users = userRepository.findAllByIdInOrderById(List.of(user1Id, user2Id));
        if (users.size() != 2) {
            throw new IllegalArgumentException("사용자 중 한 명 이상이 존재하지 않습니다.");
        }

        if(chatRoomRepository.existsByUser1AndUser2(users.getFirst(), users.getLast())) {
            throw new IllegalArgumentException("이미 동일한 채팅방이 존재합니다.");
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .user1(users.getFirst())
                .user2(users.getLast())
                .build();

        return chatRoomMapper.toResponse(chatRoomRepository.save(chatRoom));
    }

}
