package dev.cianbtlr.movies.service;

import dev.cianbtlr.movies.domain.User;
import dev.cianbtlr.movies.domain.UserRole;
import dev.cianbtlr.movies.request.RegistrationRequest;
import dev.cianbtlr.movies.validator.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid!");
        }
        return userService.singUpUser(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );
    }
}
