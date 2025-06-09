package dev.myodan.oxiom.controller;

import dev.myodan.oxiom.domain.UserPrincipal;
import dev.myodan.oxiom.dto.*;
import dev.myodan.oxiom.service.ChatMessageService;
import dev.myodan.oxiom.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat-rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || #userId eq authentication.principal.id")
    public ResponseEntity<Page<ChatRoomResponse>> getChatRooms(@RequestParam Long userId, Pageable pageable) {
        return ResponseEntity.ok(chatRoomService.getChatRoomsByUserId(userId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatRoomResponse> getChatRoom(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(chatRoomService.getChatRoomById(id, user.getId()));
    }

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestBody @Valid ChatRoomRequest chatRoomRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ChatRoomResponse chatRoomResponse = chatRoomService.createChatRoom(chatRoomRequest.userId(), userPrincipal.getId());
        URI location = URI.create("/chat-rooms/" + chatRoomResponse.id());

        return ResponseEntity.created(location).body(chatRoomResponse);
    }

    @GetMapping("/{id}/chat-messages")
    public ResponseEntity<Page<ChatMessageResponse>> getChatMessageByChatRoomId(@PathVariable Long id, Pageable pageable, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(chatMessageService.getChatMessagesByChatRoomId(id, pageable, userPrincipal.getId()));
    }

    @MessageMapping("/chat-rooms/{id}")
    @SendTo("/sub/chat-rooms/{id}")
    public ChatMessageResponse broadcastProduct(@DestinationVariable Long id, ChatMessageRequest chatMessageRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return chatMessageService.createChatMessage(id, chatMessageRequest, userPrincipal.getId());
    }

}