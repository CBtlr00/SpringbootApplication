package dev.cianbtlr.movies.repo;

import dev.cianbtlr.movies.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findUserById(String userId);

    @Transactional
    @Query("{ 'email': ?0 }")
    @Update("{ '$set': { 'enabled': true } }")
    int enableUser(String email);
}
