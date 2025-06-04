package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.Favorite;
import dev.myodan.oxiom.dto.FavoriteResponse;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ProductMapper.class}
)
public interface FavoriteMapper {

    Favorite toEntity(FavoriteResponse favoriteResponse);

    FavoriteResponse toResponse(Favorite favorite);

}