package ca.mcgill.ecse321group1.gamestore.dto;

import java.util.List;

public class CategoryListDto {
    private List<CategoryResponseDto> categories;

    public List<CategoryResponseDto> getCategories() {
        return categories;
    }

    public CategoryListDto(List<CategoryResponseDto> categories) {
        this.categories = categories;
    }

    // For Jackson
    protected CategoryListDto() {
        
    }
    
}
