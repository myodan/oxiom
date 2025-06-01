package dev.myodan.oxiom.controller;

import dev.myodan.oxiom.domain.UserPrincipal;
import dev.myodan.oxiom.dto.ProductRequest;
import dev.myodan.oxiom.dto.ProductResponse;
import dev.myodan.oxiom.dto.ProductSummaryResponse;
import dev.myodan.oxiom.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@MessageMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductSummaryResponse>> getProducts(String keyword, Long categoryId, Pageable pageable) {
        return ResponseEntity.ok(productService.getProducts(keyword, categoryId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        URI location = URI.create("/products/" + productResponse.id());

        return ResponseEntity.created(location).body(productResponse);
    }

    @MessageMapping
    public void broadcastProduct(String message, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("Message: {}", message);
        log.info("User: {}", userPrincipal.getUsername());
    }

}
