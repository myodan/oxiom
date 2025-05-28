package dev.myodan.oxiom.controller;

import dev.myodan.oxiom.dto.UserRequest;
import dev.myodan.oxiom.dto.UserResponse;
import dev.myodan.oxiom.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PermitAll
    @GetMapping
    public ResponseEntity<?> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        URI location = URI.create("/users/" + userResponse.id());

        return ResponseEntity.created(location).body(userResponse);
    }

}
