package dev.myodan.oxiom.repository;

import dev.myodan.oxiom.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p
            FROM Product p
            WHERE
                (:categoryId IS NULL OR p.category.id = :categoryId) AND
                (LOWER(p.name) LIKE CONCAT('%', LOWER(COALESCE(:keyword, '')), '%') OR LOWER(p.description) LIKE CONCAT('%', LOWER(COALESCE(:keyword, '')), '%'))
            """)
    @EntityGraph(attributePaths = {"highestBidder", "category", "createdBy"})
    Page<Product> findAllByKeywordAndCategoryId(String keyword, Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"images", "highestBidder", "category", "createdBy"})
    Optional<Product> findOneById(Long id);

}
