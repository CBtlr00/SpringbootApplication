package dev.cianbtlr.dashboard.repo;

import dev.cianbtlr.dashboard.domain.ProjService;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjServiceRepository extends MongoRepository<ProjService, ObjectId> {
    Optional<ProjService> findById(ObjectId id);
}
