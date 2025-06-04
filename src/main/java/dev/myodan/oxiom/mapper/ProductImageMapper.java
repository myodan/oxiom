package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.ProductImage;
import dev.myodan.oxiom.dto.ProductImageResponse;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ProductImageMapper {

    ProductImage toEntity(ProductImageResponse productImageResponse);

    ProductImageResponse toDto(ProductImage productImage);

}