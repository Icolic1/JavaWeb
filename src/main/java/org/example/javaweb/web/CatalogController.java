package org.example.javaweb.web;

import lombok.RequiredArgsConstructor;
import org.example.javaweb.web.dto.CategoryDto;
import org.example.javaweb.web.dto.ProductDetailsDto;
import org.example.javaweb.web.dto.ProductListDto;
import org.example.javaweb.web.mapper.CategoryMapper;
import org.example.javaweb.web.mapper.ProductMapper;
import org.example.javaweb.service.CategoryService;
import org.example.javaweb.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/categories")
    public String categories(Model model) {
        List<CategoryDto> categories = categoryService.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();

        model.addAttribute("categories", categories);
        return "catalog/categories";
    }

    @GetMapping("/categories/{id}")
    public String categoryProducts(@PathVariable Long id, Model model) {
        // category za naslov
        var category = categoryService.findById(id);
        model.addAttribute("category", CategoryMapper.toDto(category));

        // proizvodi: ako na viewu ne čitaš category.name, može i obični query
        List<ProductListDto> products = productService.findByCategoryId(id).stream()
                .map(p -> new ProductListDto(p.getId(), p.getName(), p.getPrice(), null, p.getStock()))
                .toList();

        model.addAttribute("products", products);
        return "catalog/products";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        var product = productService.findByIdWithCategory(id);
        ProductDetailsDto dto = ProductMapper.toDetailsDto(product);

        model.addAttribute("product", dto);
        return "catalog/product-details";
    }
}
