package dev.myodan.oxiom.repository;

import dev.myodan.oxiom.domain.ChatRoom;
import dev.myodan.oxiom.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @EntityGraph(attributePaths = {"user1", "user2"})
    Page<ChatRoom> findByUser1IdOrUser2Id(Long user1Id, Long user2Id, Pageable pageable);

    @EntityGraph(attributePaths = {"user1", "user2"})
    Optional<ChatRoom> findById(Long id);

    boolean existsByUser1AndUser2(User user1, User user2);

}
