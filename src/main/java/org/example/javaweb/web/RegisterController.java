package org.example.javaweb.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.AppUser;
import org.example.javaweb.repository.AppUserRepository;
import org.example.javaweb.web.dto.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("req", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("req") RegisterRequest req,
                           BindingResult br,
                           Model model) {

        if (br.hasErrors()) return "auth/register";

        if (users.existsByUsername(req.getUsername())) {
            model.addAttribute("error", "Korisničko ime je zauzeto.");
            return "auth/register";
        }

        users.save(AppUser.builder()
                .username(req.getUsername())
                .passwordHash(passwordEncoder.encode(req.getPassword())) // OVDJE encode
                .role("ROLE_USER")
                .enabled(true)
                .build());

        return "redirect:/login";
    }
}
