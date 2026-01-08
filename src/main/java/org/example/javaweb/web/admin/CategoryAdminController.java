package org.example.javaweb.web.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.javaweb.web.dto.CategoryDto;
import org.example.javaweb.web.dto.CategoryFormDto;
import org.example.javaweb.web.mapper.CategoryMapper;
import org.example.javaweb.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        populateList(model);
        model.addAttribute("categoryForm", new CategoryFormDto());
        return "admin/categories/index";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("categoryForm") CategoryFormDto form,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            populateList(model);
            return "admin/categories/index";
        }

        categoryService.create(CategoryMapper.toEntity(form));
        return "redirect:/admin/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }

    private void populateList(Model model) {
        List<CategoryDto> categories = categoryService.findAll().stream()
                .map(CategoryMapper::toDto)
                .toList();
        model.addAttribute("categories", categories);
    }
}
