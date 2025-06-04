package dev.myodan.oxiom.controller;

import dev.myodan.oxiom.domain.UserPrincipal;
import dev.myodan.oxiom.dto.ChatMessageRequest;
import dev.myodan.oxiom.dto.ChatMessageResponse;
import dev.myodan.oxiom.dto.ChatRoomRequest;
import dev.myodan.oxiom.dto.ChatRoomResponse;
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
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || #id eq authentication.principal.id")
    public ResponseEntity<Page<ChatRoomResponse>> getChatRoom(@RequestParam Long id, Pageable pageable) {
        return ResponseEntity.ok(chatRoomService.getChatRoomsByUserId(id, pageable));
    }

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestBody @Valid ChatRoomRequest chatRoomRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ChatRoomResponse chatRoomResponse = chatRoomService.createChatRoom(userPrincipal.getId(), chatRoomRequest.userId());
        URI location = URI.create("/chatrooms/" + chatRoomResponse.id());

        return ResponseEntity.created(location).body(chatRoomResponse);
    }

    @MessageMapping("/chatrooms/{id}")
    @SendTo("/sub/chatrooms/{id}")
    public ChatMessageResponse broadcastProduct(@DestinationVariable Long id, ChatMessageRequest chatMessageRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return chatMessageService.createChatMessage(id, userPrincipal.getId(), chatMessageRequest);
    }

}
