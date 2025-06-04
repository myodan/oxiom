package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.Category;
import dev.myodan.oxiom.dto.CategoryResponse;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CategoryMapper {

    Category toEntity(CategoryResponse categoryResponse);

    CategoryResponse toResponse(Category category);

}