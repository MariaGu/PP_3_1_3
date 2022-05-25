package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;


@Component
public class AuthorizationService {
    private AuthenticationManager authenticationManager;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDetailsService userDetailsService;
    private UserService userService;

    @Autowired
    public AuthorizationService(AuthenticationManager authenticationManager,
                                AuthenticationManagerBuilder authenticationManagerBuilder,
                                UserDetailsService userDetailsService,
                                UserService userService) {
        this.authenticationManager = authenticationManager;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = (User) userService.loadUserByUsername(username);
        return user != null ? user : new User();

    }
}
