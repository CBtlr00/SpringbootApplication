package dev.cianbtlr.movies.repo;

import dev.cianbtlr.movies.domain.token.ConfirmationToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, ObjectId> {
    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Query("{ 'token': ?0 }")
    @Update("{ '$set': { 'confirmedAt': ?1 } }")
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
