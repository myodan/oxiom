package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.Product;
import dev.myodan.oxiom.dto.ProductRequest;
import dev.myodan.oxiom.dto.ProductResponse;
import dev.myodan.oxiom.dto.ProductSummaryResponse;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ProductImageMapper.class, UserMapper.class}
)
public interface ProductMapper {

    Product toEntity(ProductResponse productResponse);

    Product toEntity(ProductRequest productRequest);

    @AfterMapping
    default void linkImages(@MappingTarget Product product) {
        product.getImages().forEach(image -> image.setProduct(product));
    }

    ProductResponse toResponse(Product product);

    ProductSummaryResponse toSummaryResponse(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductResponse productResponse, @MappingTarget Product product);

}