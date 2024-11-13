package ca.mcgill.ecse321group1.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

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
     * 
     * @param categoryToCreate The category to create.
     * @return The created category, including the ID.
     */
    @PostMapping("/category")
    public CategoryResponseDto createCategory(@Valid @RequestBody CategoryRequestDto categoryToCreate) {
        Category createdCategory = categoryService.createCategory(categoryToCreate.getName(), categoryToCreate.getDescription());
        return new CategoryResponseDto(createdCategory);
    }

    /**
     * Get category by ID
     * 
     * @param cid The primary key (category ID) of the category to find.
     * @return The category with the given ID.
     */
    @GetMapping("/category/{cid}")
    public CategoryResponseDto findCategoryById(@PathVariable int cid) {
        Category createdCategory = categoryService.getCategory(cid);
        return new CategoryResponseDto(createdCategory);
    }

    /**
     * Delete category by ID
     * 
     * @param cid The primary key (category ID) of the category to delete.
     */
    @DeleteMapping("/category/{cid}")
    public void deleteCategoryById(@PathVariable int cid) {
        categoryService.deleteCategory(cid);
    }
}
