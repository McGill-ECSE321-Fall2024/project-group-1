package ca.mcgill.ecse321group1.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;


import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.service.CategoryService;
import ca.mcgill.ecse321group1.gamestore.model.Category;



@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * Create category
     */
    @PostMapping("/category")
    public CategoryResponseDto createCategory(@Valid @RequestBody CategoryRequestDto categoryToCreate) {
        Category createdCategory = categoryService.createCategory(categoryToCreate.getName(), categoryToCreate.getDescription());
        return new CategoryResponseDto(createdCategory);
    }
}
