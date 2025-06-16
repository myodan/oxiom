package dev.myodan.oxiom.mapper;

import dev.myodan.oxiom.domain.Bid;
import dev.myodan.oxiom.dto.BidRequest;
import dev.myodan.oxiom.dto.BidResponse;
import dev.myodan.oxiom.dto.ProductBidResponse;
import dev.myodan.oxiom.dto.UserBidResponse;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ProductMapper.class, UserMapper.class}
)
public interface BidMapper {

    Bid toEntity(BidResponse bidResponse);

    Bid toEntity(BidRequest bidRequest);

    BidResponse toResponse(Bid bid);

    ProductBidResponse toProductBidResponse(Bid bid);

    UserBidResponse toUserBidResponse(Bid bid);

}