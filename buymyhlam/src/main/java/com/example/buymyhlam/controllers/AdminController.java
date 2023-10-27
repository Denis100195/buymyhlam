package com.example.buymyhlam.controllers;

import com.example.buymyhlam.models.User;
import com.example.buymyhlam.models.enums.Role;
import com.example.buymyhlam.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping
    public String admin(Model model, Principal principal) {
        model.addAttribute("users", userService.list());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
    }
    @PostMapping("user/ban/{id}")
    public String userBan(@PathVariable("id") Long id){
        userService.banUser(id);
        return "redirect:/admin";
    }
    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }
    @PostMapping("/user/edit/")
    public String userEdit(@RequestParam("userId") User user, @RequestParam Map<String, String> form){
        userService.changeUserRole(user, form);
        return "redirect:/admin";
    }
}
