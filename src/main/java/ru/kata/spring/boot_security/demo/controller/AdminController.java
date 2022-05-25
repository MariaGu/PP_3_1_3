package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.AuthorizationService;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final AuthorizationService authorizationService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.roleService = roleService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping("/new")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/new")
    public String addUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("{id}/edit")
    public String editUser(@PathVariable("id") int id, User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("{id}/delete")
    public String editUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

//    @GetMapping("/")
//    public String login() {
//        return "login";
//    }
}
