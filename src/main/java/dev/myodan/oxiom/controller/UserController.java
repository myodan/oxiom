package dev.myodan.oxiom.controller;

import dev.myodan.oxiom.domain.UserPrincipal;
import dev.myodan.oxiom.dto.*;
import dev.myodan.oxiom.service.BidService;
import dev.myodan.oxiom.service.NotificationService;
import dev.myodan.oxiom.service.ProductService;
import dev.myodan.oxiom.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BidService bidService;
    private final ProductService productService;
    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id eq authentication.principal.id")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        UserResponse userResponse = userService.createUser(userCreateRequest);
        URI location = URI.create("/users/" + userResponse.id());

        return ResponseEntity.created(location).body(userResponse);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id eq authentication.principal.id")
    public ResponseEntity<UserResponse> partialUpdateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.partialUpdateUser(id, userUpdateRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserByMe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userService.getUser(userPrincipal.getId()));
    }

    @GetMapping("/me/notifications")
    public ResponseEntity<Page<NotificationResponse>> getUserNotifications(@AuthenticationPrincipal UserPrincipal userPrincipal, Pageable pageable) {
        return  ResponseEntity.ok(notificationService.getNotificationsByUserId(userPrincipal.getId(), pageable));
    }

    @GetMapping("/me/products")
    public ResponseEntity<Page<UserProductResponse>> getUserProducts(@AuthenticationPrincipal UserPrincipal userPrincipal, Pageable pageable) {
        return ResponseEntity.ok(productService.getProductsByCreatedById(userPrincipal.getId(), pageable));
    }

    @GetMapping("/me/bids")
    public ResponseEntity<Page<UserBidResponse>> getUserBids(@AuthenticationPrincipal UserPrincipal userPrincipal, Pageable pageable) {
        return ResponseEntity.ok(bidService.getBidsByUserId(userPrincipal.getId(), pageable));
    }

}
