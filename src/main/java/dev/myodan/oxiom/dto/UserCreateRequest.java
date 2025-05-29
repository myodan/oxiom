package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserCreateRequest(

        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String displayName

) {
}