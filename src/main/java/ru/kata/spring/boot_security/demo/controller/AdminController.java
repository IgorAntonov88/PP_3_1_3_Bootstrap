package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/admin")
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "adminPage";
    }
    @GetMapping( "/admin/new")
    public String newUser(Model model) {
        model.addAttribute("person", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "new";
    }
    @PostMapping("/admin")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "edit";
    }
    @PostMapping("/{id}/update")
    public String editUser(@PathVariable("id") int id, @ModelAttribute User user, Model model) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/{id}/delete")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
