package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.domain.User.Role;
import dev.myodan.oxiom.dto.UserCreateRequest;
import dev.myodan.oxiom.dto.UserResponse;
import dev.myodan.oxiom.dto.UserUpdateRequest;
import dev.myodan.oxiom.mapper.UserMapper;
import dev.myodan.oxiom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        return userRepository.findById(id).map(userMapper::toResponse).orElseThrow(
                () -> new IllegalArgumentException("User not found.")
        );
    }

    @Transactional
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        boolean exists = userRepository.existsByUsernameAndEmail(userCreateRequest.username(), userCreateRequest.email());

        if (exists) {
            throw new IllegalArgumentException("Username or email is already in use.");
        }

        User user = userMapper.toEntity(userCreateRequest);
        user.setPassword(passwordEncoder.encode(userCreateRequest.password()));
        user.setRole(Role.ROLE_USER);

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse partialUpdateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found.")
        );

        userMapper.partialUpdate(userUpdateRequest, user);

        return userMapper.toResponse(user);
    }

}
