package dev.cianbtlr.dashboard.repo;

import dev.cianbtlr.dashboard.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(ObjectId id);

    Optional<User> findUserByUserId(String userId);

    @Transactional
    @Query("{ 'email': ?0 }")
    @Update("{ '$set': { 'enabled': true } }")
    int enableUser(String email);
}
