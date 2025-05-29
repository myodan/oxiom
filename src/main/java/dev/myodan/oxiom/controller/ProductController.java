package dev.myodan.oxiom.controller;

import dev.myodan.oxiom.dto.ProductRequest;
import dev.myodan.oxiom.dto.ProductResponse;
import dev.myodan.oxiom.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getProducts(pageable));
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

}
