package dev.myodan.oxiom.repository;

import dev.myodan.oxiom.domain.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    Page<ChatMessage> findByChatRoomId(Long chatRoomId, Pageable pageable);

    @Aggregation(pipeline = {
            "{ '$match': { 'chatRoomId': { '$in': ?0 } } }",
            "{ '$sort': { 'createDate': -1 } }",
            "{ '$group': { '_id': '$chatRoomId', 'doc': { '$first': '$$ROOT' } } }",
            "{ '$replaceRoot': { 'newRoot': '$doc' } }"
    })
    List<ChatMessage> findLatestMessagesByChatRoomIds(List<Long> chatRoomIds);

    ChatMessage findFirstByChatRoomIdOrderByCreatedDate(Long chatRoomId);

}
