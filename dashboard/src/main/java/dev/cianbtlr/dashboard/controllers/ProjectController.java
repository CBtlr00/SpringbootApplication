package dev.cianbtlr.dashboard.controllers;

import dev.cianbtlr.dashboard.domain.Project;
import dev.cianbtlr.dashboard.domain.enums.ProjectType;
import dev.cianbtlr.dashboard.request.ProjectCreateRequest;
import dev.cianbtlr.dashboard.service.ProjectService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.allProjects();
    }

    @GetMapping("/{id}")
    public Optional<Project> getProjectById(@PathVariable ObjectId id) {
        return projectService.singleProject(id);
    }

    @GetMapping("/user/{userId}")
    public List<Project> getProjectsByUserId(@PathVariable ObjectId userId) {
        return projectService.findAllProjectsByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody ProjectCreateRequest request) {
        ProjectType projectType;
        try {
            projectType = ProjectType.valueOf(request.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid project type provided: " + request.getType());
        }

        return projectService.createProject(
                new Project(request.getName(), projectType, null),
                request.getUserId(),
                request.getServiceIds()
        );
    }

    @PutMapping("/{projectId}")
    public Project updateProject(@PathVariable ObjectId projectId, @RequestParam ObjectId userId, @RequestBody String newName) {
        return projectService.updateProject(projectId, userId, newName);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable ObjectId projectId, @RequestParam ObjectId userId) {
        projectService.deleteProject(projectId, userId);
    }

    @PostMapping("/{projectId}/services/{serviceId}")
    public Project addServiceToProject(
            @PathVariable ObjectId projectId,
            @PathVariable ObjectId serviceId,
            @RequestParam ObjectId userId) {
        projectService.addServiceToProject(projectId, serviceId, userId);
        return projectService.singleProject(projectId).orElseThrow(() -> new IllegalStateException("Project not found"));
    }
}