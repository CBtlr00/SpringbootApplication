package dev.cianbtlr.dashboard.service;

import dev.cianbtlr.dashboard.domain.ProjService;
import dev.cianbtlr.dashboard.domain.Project;
import dev.cianbtlr.dashboard.domain.User;
import dev.cianbtlr.dashboard.repo.ProjectRepository;
import dev.cianbtlr.dashboard.repo.ProjServiceRepository;
import dev.cianbtlr.dashboard.repo.UserRepository;
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
    private ProjServiceRepository projServiceRepository;

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

    public Project createProject(Project newProject, ObjectId id, List<ObjectId> serviceIds) {
        User owner = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (serviceIds == null || serviceIds.isEmpty()) {
            throw new IllegalStateException("At least one service must be included when creating a project.");
        }

        List<ProjService> services = projServiceRepository.findAllById(serviceIds);
        if (services.size() != serviceIds.size()) {
            throw new IllegalStateException("Some of the provided services do not exist.");
        }

        newProject.setOwner(owner);
        newProject.setServices(services);

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

    public void addServiceToProject(ObjectId projectId, ObjectId serviceId, ObjectId userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalStateException("Project not found"));

        if (!project.getOwner().getId().equals(userId)) {
            throw new IllegalStateException("User is not the owner of this project");
        }

        ProjService service = projServiceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalStateException("Service not found"));

        project.addService(service);
        projectRepository.save(project);
    }

}