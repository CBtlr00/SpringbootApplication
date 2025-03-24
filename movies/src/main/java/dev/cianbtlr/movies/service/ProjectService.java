package dev.cianbtlr.movies.service;

import dev.cianbtlr.movies.domain.Project;
import dev.cianbtlr.movies.domain.User;
import dev.cianbtlr.movies.repo.ProjectRepository;
import dev.cianbtlr.movies.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService {
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    public List<Project> allProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> singleProject(ObjectId id) {
        return projectRepository.findProjectById(id);
    }

    public List<Project> findAllProjectsByUserId(ObjectId userId) {
        return projectRepository.findByOwnerId(userId);
    }

    public Optional<Project> findProjectByIdAndUserId(ObjectId projectId, ObjectId userId) {
        return projectRepository.findProjectByIdAndOwnerId(projectId, userId);
    }

    public Project createProject(Project newProject, ObjectId id) {
        User owner = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        newProject.setOwner(owner);
        return projectRepository.save(newProject);
    }

    public Project updateProject(ObjectId projectId, ObjectId userId, String newName) {
        Project project = projectRepository.findProjectByIdAndOwnerId(projectId, userId)
                .orElseThrow(() -> new IllegalStateException("Project not found or does not belong to the user"));

        project.setName(newName);

        return projectRepository.save(project);
    }

    public void deleteProject(ObjectId projectId, ObjectId userId) {
        Project project = projectRepository.findProjectByIdAndOwnerId(projectId, userId)
                .orElseThrow(() -> new IllegalStateException("Project not found or does not belong to the user"));

        projectRepository.delete(project);
    }
}
