package ca.mcgill.ecse321group1.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryListDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.service.CategoryService;
import ca.mcgill.ecse321group1.gamestore.model.Category;



@RestController
@CrossOrigin(origins = "http://localhost:8087")
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
     * Get all categories
     * 
     * @return The list of all categories as a CategoryListDto
     */
    @GetMapping("/category")
    public CategoryListDto getAllCategories() {
        List<Category> categories = categoryService.getAll();
        List<CategoryResponseDto> dtoList = new ArrayList<>();

        for (Category category : categories) {
            dtoList.add(new CategoryResponseDto(category));
        }

        return new CategoryListDto(dtoList);
        
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
     * Retrieves category by ID and allows attributes to be edited.
     * 
     * @param cid The primary key (category ID) of the category to edit.
     * @param request The category request DTO with new name and new description
     * @return The category with changed attributes.
     */
    @PutMapping("/category/{cid}")
    public CategoryResponseDto editCategoryById(@PathVariable int cid, @Valid @RequestBody CategoryRequestDto request) {
        Category editedCategory = categoryService.editCategory(cid, request.getName(), request.getDescription());
        return new CategoryResponseDto(editedCategory);
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
