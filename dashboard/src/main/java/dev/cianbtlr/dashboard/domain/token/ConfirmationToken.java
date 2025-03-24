package dev.cianbtlr.dashboard.domain.token;

import dev.cianbtlr.dashboard.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "confirmation_tokens")
@Data
public class ConfirmationToken {
    @Id
    private ObjectId id;
    private String token;
    private LocalDateTime createAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmAt;
    private User user;

    public ConfirmationToken(String token, LocalDateTime createAt, LocalDateTime expiredAt, User user) {
        this.token = token;
        this.createAt = createAt;
        this.expiresAt = expiredAt;
        this.user = user;
    }
}