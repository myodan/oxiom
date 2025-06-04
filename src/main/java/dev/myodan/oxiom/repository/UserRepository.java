package dev.myodan.oxiom.repository;

import dev.myodan.oxiom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsernameAndEmail(String username, String email);

    List<User> findAllByIdInOrderById(Collection<Long> ids);

}
