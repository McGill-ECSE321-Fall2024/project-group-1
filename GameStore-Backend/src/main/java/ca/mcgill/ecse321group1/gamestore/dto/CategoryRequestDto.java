package ca.mcgill.ecse321group1.gamestore.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {
    //to create category need name and description
    //to edit need id, name and desc
    //to delete need id
    //to get category need id 
    
    @NotBlank(message = "Category name is required.")
    private String name;
    @NotBlank(message = "Description is required.")
    private String description;

    public CategoryRequestDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
