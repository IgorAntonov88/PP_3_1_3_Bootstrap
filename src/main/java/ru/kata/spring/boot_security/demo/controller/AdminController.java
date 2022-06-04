package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/admin")
    public String findAll(Principal principal, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("person", new User());
        return "adminPage";
    }
//    @GetMapping( "/admin/new")
//    public String newUser(Model model) {
//        model.addAttribute("person", new User());
//        model.addAttribute("roles", roleRepository.findAll());
//        return "new";
//    }
    @PostMapping("/admin/new")
    public String addUser(@ModelAttribute("person") User user) {
            userService.saveUser(user);
            return "redirect:/admin";
    }
    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "edit";
    }
    @PatchMapping("/admin/update/{id}")
    public String editUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
