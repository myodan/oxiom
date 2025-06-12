package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.Product;
import dev.myodan.oxiom.domain.ProductImage;
import dev.myodan.oxiom.dto.ProductRequest;
import dev.myodan.oxiom.dto.ProductResponse;
import dev.myodan.oxiom.dto.ProductSummaryResponse;
import dev.myodan.oxiom.mapper.ProductMapper;
import dev.myodan.oxiom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UploadService uploadService;

    @Transactional(readOnly = true)
    public Page<ProductSummaryResponse> getProducts(String keyword, Long categoryId, Pageable pageable) {
        return productRepository.findAllByKeywordAndCategoryId(keyword, categoryId, pageable).map(productMapper::toSummaryResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        return productRepository.findOneById(id).map(productMapper::toResponse).orElseThrow(
                () -> new IllegalArgumentException("Product not found.")
        );
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        product.setCurrentPrice(product.getInitialPrice());

        Product savedProduct = productRepository.save(product);

        for (ProductImage productImage : savedProduct.getImages()) {
            if (productImage.getObjectKey().startsWith("http")) {
                continue;
            }
            List<String> objectKeyParts = List.of(productImage.getObjectKey().split("/"));
            String newObjectKey = String.format("products/%s/%s", savedProduct.getId(), objectKeyParts.getLast());
            uploadService.moveObject(productImage.getObjectKey(), newObjectKey);
            productImage.setObjectKey(newObjectKey);
        }

        return productMapper.toResponse(savedProduct);
    }

}
