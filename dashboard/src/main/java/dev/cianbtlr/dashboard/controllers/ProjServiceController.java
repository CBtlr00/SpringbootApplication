package dev.cianbtlr.dashboard.controllers;

import dev.cianbtlr.dashboard.domain.ProjService;
import dev.cianbtlr.dashboard.service.ProjServiceService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/services")
@AllArgsConstructor
public class ProjServiceController {
    private final ProjServiceService projServiceService;

    @GetMapping
    public List<ProjService> getAllServices() {
        return projServiceService.allProjects();
    }

    @GetMapping("/{id}")
    public Optional<ProjService> getServiceById(@PathVariable ObjectId id) {
        return projServiceService.singleProject(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjService createService(@RequestBody ProjService newService) {
        return projServiceService.createService(newService);
    }

    @PutMapping("/{id}")
    public Optional<ProjService> updateService(
            @PathVariable ObjectId id,
            @RequestBody String newName) {
        return projServiceService.updateService(id, newName);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable ObjectId id) {
        boolean deleted = projServiceService.deleteService(id);
        if (!deleted) {
            throw new RuntimeException("Service not found with ID: " + id);
        }
    }
}
