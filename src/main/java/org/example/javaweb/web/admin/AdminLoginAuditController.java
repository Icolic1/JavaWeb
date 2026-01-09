package org.example.javaweb.web.admin;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.LoginAudit;
import org.example.javaweb.repository.LoginAuditRepository;
import org.example.javaweb.repository.spec.LoginAuditSpecifications;
import org.example.javaweb.web.mapper.LoginAuditMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/login-audit")
public class AdminLoginAuditController {

    private final LoginAuditRepository loginAuditRepository;

    @GetMapping
    public String index(@RequestParam(required = false) String username,
                        @RequestParam(required = false) LocalDate from,
                        @RequestParam(required = false) LocalDate to,
                        Model model) {

        Specification<LoginAudit> spec = Specification
                .where(LoginAuditSpecifications.usernameContainsIgnoreCase(username))
                .and(LoginAuditSpecifications.loginAtFrom(from))
                .and(LoginAuditSpecifications.loginAtTo(to));

        var audits = loginAuditRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "loginAt"))
                .stream()
                .map(LoginAuditMapper::toListDto)
                .toList();

        model.addAttribute("audits", audits);
        model.addAttribute("username", username);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        return "admin/login-audit/index";
    }
}
