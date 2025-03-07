package dev.cianbtlr.movies.controllers;

import dev.cianbtlr.movies.request.LoginRequest;
import dev.cianbtlr.movies.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public String register(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
