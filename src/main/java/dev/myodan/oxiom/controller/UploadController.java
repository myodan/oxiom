package dev.myodan.oxiom.controller;

import dev.myodan.oxiom.dto.UploadResponse;
import dev.myodan.oxiom.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/uploads")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/pre-signed-url")
    public ResponseEntity<UploadResponse> upload() {
        String objectKey = String.format("temp/%s", UUID.randomUUID());
        Duration duration = Duration.ofMinutes(10);

        return ResponseEntity.ok(UploadResponse.builder()
                .url(uploadService.generatePresignedUploadUrl(objectKey, duration))
                .key(objectKey)
                .expiresIn(duration.toSeconds())
                .build()
        );
    }

}
