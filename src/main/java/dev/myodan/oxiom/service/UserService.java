package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.dto.UserRequest;
import dev.myodan.oxiom.domain.Role;
import dev.myodan.oxiom.dto.UserResponse;
import dev.myodan.oxiom.mapper.UserMapper;
import dev.myodan.oxiom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponse);
    }

    public UserResponse createUser(UserRequest userRequest) {
        boolean exists = userRepository.existsByUsernameAndEmail(userRequest.username(), userRequest.email());

        if (exists) {
            throw new IllegalArgumentException("Username or email is already in use.");
        }

        User user = User.builder()
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .email(userRequest.email())
                .role(Role.ROLE_USER)
                .build();

        return userMapper.toResponse(userRepository.save(user));
    }

}
