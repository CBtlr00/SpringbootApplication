package dev.cianbtlr.dashboard.domain;

import dev.cianbtlr.dashboard.domain.enums.ServiceType;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "services")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjService {
    @Id
    private ObjectId id;
    private String name;
    private ServiceType type;
    //List of keys

    public ProjService(String name, ServiceType type) {
        this.name = name;
        this.type = type;
    }
}
