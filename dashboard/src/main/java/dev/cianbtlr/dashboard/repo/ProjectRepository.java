package dev.cianbtlr.dashboard.repo;

import dev.cianbtlr.dashboard.domain.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, ObjectId> {
    Optional<Project> findByName(String name);

    Optional<Project> findProjectById(ObjectId id);

    List<Project> findByOwnerId(ObjectId ownerId);

    Optional<Project> findProjectByIdAndOwnerId(ObjectId projectId, ObjectId ownerId);
}
