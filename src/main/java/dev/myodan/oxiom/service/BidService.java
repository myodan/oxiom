package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.Bid;
import dev.myodan.oxiom.domain.Product;
import dev.myodan.oxiom.dto.BidRequest;
import dev.myodan.oxiom.dto.BidResponse;
import dev.myodan.oxiom.mapper.BidMapper;
import dev.myodan.oxiom.repository.BidRepository;
import dev.myodan.oxiom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BidService {

    private final BidRepository bidRepository;
    private final BidMapper bidMapper;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<BidResponse> getBidsByProductId(Long productId, Pageable pageable) {
        return bidRepository.findAllByProductId(productId, pageable).map(bidMapper::toResponse);
    }

    @Transactional
    public BidResponse createBidByProductId(Long productId, BidRequest bidRequest) {
        Product product = productRepository.findOneById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not found.")
        );

        Bid bid = bidMapper.toEntity(bidRequest);
        bid.setProduct(product);

        return bidMapper.toResponse(bidRepository.save(bid));
    }

}
