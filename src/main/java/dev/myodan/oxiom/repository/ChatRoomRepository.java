package dev.myodan.oxiom.repository;

import dev.myodan.oxiom.domain.ChatRoom;
import dev.myodan.oxiom.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @EntityGraph(attributePaths = {"user1", "user2"})
    Page<ChatRoom> findByUser1IdOrUser2Id(Long user1Id, Long user2Id, Pageable pageable);

    boolean existsByUser1AndUser2(User user1, User user2);

}
