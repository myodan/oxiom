package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.Product;
import dev.myodan.oxiom.domain.Product.Status;
import dev.myodan.oxiom.dto.ProductRequest;
import dev.myodan.oxiom.dto.ProductResponse;
import dev.myodan.oxiom.dto.ProductSummaryResponse;
import dev.myodan.oxiom.dto.UserProductResponse;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ProductImageMapper.class, UserMapper.class}
)
public interface ProductMapper {

    Product toEntity(ProductResponse productResponse);

    Product toEntity(ProductSummaryResponse productSummaryResponse);

    Product toEntity(ProductRequest productRequest);

    ProductResponse toResponse(Product product);

    ProductSummaryResponse toSummaryResponse(Product product);

    UserProductResponse toUserProductResponse(Product product);

    @AfterMapping
    default void linkImages(@MappingTarget Product product) {
        product.getImages().forEach(image -> image.setProduct(product));
    }

    @AfterMapping
    default void setDefaultStatus(@MappingTarget Product product) {
        if (product.getStatus() == null) {
            product.setStatus(Status.OPEN);
        }
    }

}