package dev.cianbtlr.dashboard.domain;

import dev.cianbtlr.dashboard.domain.enums.ProjectType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "projects")
@Data
public class Project {
    @Id
    private ObjectId id;
    private String name;
    private ProjectType type;
    @DBRef
    private User owner; // Many-to-One relationship with User (owner of the project)
    private List<ProjService> services = new ArrayList<>();

    public Project(String name, ProjectType type, User owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public void addService(ProjService service) {
        if (this.type.name().equals(service.getType().name())) {
            this.services.add(service);
        } else {
            throw new IllegalArgumentException("Service type does not match project type");
        }
    }
}
