package dev.cianbtlr.movies.service;

import dev.cianbtlr.movies.domain.User;
import dev.cianbtlr.movies.domain.token.ConfirmationToken;
import dev.cianbtlr.movies.repo.UserRepository;
import dev.cianbtlr.movies.request.LoginRequest;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dev.cianbtlr.movies.security.jwt.JwtService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private final static String EMAIL_NOT_FOUND = "User with email %s not found!";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtService jwtService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND, email)));
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Optional<User> singleUser(String userId) { return userRepository.findUserByUserId(userId); }

    public String singUpUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
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

        try {
            AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new IllegalStateException("Authentication failed!", e);
        }

        return jwtService.generateToken(user);
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
