package org.example.javaweb.web.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.Category;
import org.example.javaweb.web.dto.CategoryDto;
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
        List<CategoryDto> categories = categoryService.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();

        model.addAttribute("categories", categories);
        model.addAttribute("categoryForm", new Category()); // ili CategoryFormDto ako želiš; za sad minimalno
        return "admin/categories/index";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("categoryForm") Category category,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            List<CategoryDto> categories = categoryService.findAll()
                    .stream()
                    .map(CategoryMapper::toDto)
                    .toList();

            model.addAttribute("categories", categories);
            return "admin/categories/index";
        }

        categoryService.create(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }
}
