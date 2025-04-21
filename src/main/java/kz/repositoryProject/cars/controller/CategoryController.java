package kz.repositoryProject.cars.controller;

import kz.repositoryProject.cars.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/addcategory")
    public String addCategoryPage() {
        return "add-category";
    }

    @PostMapping("/addcategory")
    public String addCategory(
            @RequestParam(name = "category_name") String name) {
        categoryService.addCategory(name);
        return "redirect:/";
    }
}
