package dev.myodan.oxiom.repository;

import dev.myodan.oxiom.domain.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @EntityGraph(attributePaths = {"createdBy"})
    Page<Bid> findAllByProductId(Long productId, Pageable pageable);

    @EntityGraph(attributePaths = {"product", "product.category", "product.createdBy"})
    Page<Bid> findAllByCreatedById(Long id, Pageable pageable);

}
