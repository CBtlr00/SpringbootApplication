package dev.cianbtlr.movies.service;

import dev.cianbtlr.movies.domain.User;
import dev.cianbtlr.movies.domain.UserRole;
import dev.cianbtlr.movies.domain.token.ConfirmationToken;
import dev.cianbtlr.movies.repo.UserRepository;
import dev.cianbtlr.movies.request.LoginRequest;
import dev.cianbtlr.movies.request.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private final static String EMAIL_NOT_FOUND = "User with email %s not found!";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND, email)));
    }

    public String singUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already exists!");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Invalid email or password!");
        }

        if (!user.isEnabled()) {
            throw new IllegalStateException("User is not enabled. Please confirm your email.");
        }

        // TODO: Authenticate user
        //        authenticationManager.authenticate(
        //                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        //        );
        // TODO: Generate JWT token and return it
        return "Login successful";
    }


    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
