package dev.cianbtlr.movies.controllers;

import dev.cianbtlr.movies.domain.Project;
import dev.cianbtlr.movies.service.ProjectService;
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
    public Project createProject(@RequestBody Project project, @RequestParam ObjectId userId) {
        return projectService.createProject(project, userId);
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
}