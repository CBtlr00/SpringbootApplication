package dev.cianbtlr.dashboard.service;

import dev.cianbtlr.dashboard.domain.ProjService;
import dev.cianbtlr.dashboard.repo.ProjServiceRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjServiceService {
    private ProjServiceRepository projServiceRepository;

    public List<ProjService> allProjects() {
        return projServiceRepository.findAll();
    }

    public Optional<ProjService> singleProject(ObjectId id) {
        return projServiceRepository.findById(id);
    }

    public ProjService createService(ProjService newService) {
        return projServiceRepository.save(newService);
    }

    public Optional<ProjService> updateService(ObjectId id, String newName) {
        Optional<ProjService> existingService = projServiceRepository.findById(id);

        if (existingService.isPresent()) {
            ProjService service = existingService.get();
            service.setName(newName);
            return Optional.of(projServiceRepository.save(service));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteService(ObjectId id) {
        Optional<ProjService> existingService = projServiceRepository.findById(id);

        if (existingService.isPresent()) {
            projServiceRepository.delete(existingService.get());
            return true;
        }

        return false;
    }
}
