package org.example.javaweb.web.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.javaweb.domain.Category;
import org.example.javaweb.domain.Product;
import org.example.javaweb.web.dto.ProductFormDto;
import org.example.javaweb.web.mapper.ProductMapper;
import org.example.javaweb.service.CategoryService;
import org.example.javaweb.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("products",
                productService.findAllWithCategory().stream()
                        .map(ProductMapper::toListDto)
                        .collect(Collectors.toList())
        );

        model.addAttribute("productForm", new ProductFormDto());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/products/index";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("productForm") ProductFormDto form,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("products",
                    productService.findAllWithCategory().stream()
                            .map(ProductMapper::toListDto)
                            .collect(Collectors.toList())
            );
            model.addAttribute("categories", categoryService.findAll());
            return "admin/products/index";
        }

        Category category = categoryService.findById(form.getCategoryId());

        Product product = Product.builder()
                .name(form.getName())
                .description(form.getDescription())
                .price(form.getPrice())
                .stock(form.getStock())
                .category(category)
                .build();

        productService.create(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.findByIdWithCategory(id);

        model.addAttribute("productForm", ProductMapper.toFormDto(product));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/products/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("productForm") ProductFormDto form,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/products/edit";
        }

        Category category = categoryService.findById(form.getCategoryId());

        Product product = Product.builder()
                .name(form.getName())
                .description(form.getDescription())
                .price(form.getPrice())
                .stock(form.getStock())
                .category(category)
                .build();

        productService.update(id, product);
        return "redirect:/admin/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}
