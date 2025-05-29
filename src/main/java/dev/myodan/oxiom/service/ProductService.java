package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.Product;
import dev.myodan.oxiom.dto.ProductRequest;
import dev.myodan.oxiom.dto.ProductResponse;
import dev.myodan.oxiom.mapper.ProductMapper;
import dev.myodan.oxiom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        return productRepository.findById(id).map(productMapper::toResponse).orElseThrow(
                () -> new IllegalArgumentException("Product not found.")
        );
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        product.setCurrentPrice(product.getInitialPrice());

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

}
