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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BidService {

    private final BidRepository bidRepository;
    private final BidMapper bidMapper;
    private final ProductRepository productRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional(readOnly = true)
    public Page<BidResponse> getBidsByProductId(Long productId, Pageable pageable) {
        return bidRepository.findAllByProductId(productId, pageable).map(bidMapper::toResponse);
    }

    @Transactional
    public BidResponse createBid(Long productId, BidRequest bidRequest) {
        Product product = productRepository.findOneById(productId).orElseThrow(
                () -> new IllegalArgumentException("상품을 찾을 수 없습니다.")
        );

        if (product.getCurrentPrice() >= bidRequest.price()) {
            throw new IllegalArgumentException("요청한 입찰의 금액이 기존 입찰 금액보다 적습니다.");
        }

        Bid bid = bidMapper.toEntity(bidRequest);
        bid.setProduct(product);

        product.setCurrentPrice(bidRequest.price());

        BidResponse bidResponse = bidMapper.toResponse(bidRepository.save(bid));

        simpMessagingTemplate.convertAndSend(String.format("/sub/products/%d", productId), bidResponse);

        return bidResponse;
    }

}
