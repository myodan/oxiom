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
            select p
            from Product p
            where
                (:categoryId is null or p.category.id = :categoryId) and
                (lower(p.name) like concat('%', lower(coalesce(:keyword, '')), '%') or lower(p.description) like concat('%', lower(coalesce(:keyword, '')), '%'))
            """)
    @EntityGraph(attributePaths = {"highestBidder", "category", "createdBy"})
    Page<Product> findAllByKeywordAndCategoryId(String keyword, Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"images", "highestBidder", "category", "createdBy"})
    Optional<Product> findOneById(Long id);

}
