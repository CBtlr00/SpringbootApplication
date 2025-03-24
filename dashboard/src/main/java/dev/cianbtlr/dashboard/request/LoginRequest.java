package dev.cianbtlr.dashboard.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class LoginRequest {
    private final String email;
    private final String password;
}
