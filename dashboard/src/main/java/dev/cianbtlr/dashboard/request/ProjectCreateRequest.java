package dev.cianbtlr.dashboard.request;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class ProjectCreateRequest {
    private String name;
    private String type;
    private ObjectId userId;
    private List<ObjectId> serviceIds;
}