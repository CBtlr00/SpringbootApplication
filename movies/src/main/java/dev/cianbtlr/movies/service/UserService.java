package dev.cianbtlr.movies.service;

import dev.cianbtlr.movies.domain.User;
import dev.cianbtlr.movies.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private final static String EMAIL_NOT_FOUND = "User with email %s not found!";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        // TODO: Send confirmation token
        return "It works!";
    }
}
