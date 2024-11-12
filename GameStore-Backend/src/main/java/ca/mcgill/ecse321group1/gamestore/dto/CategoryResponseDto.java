package ca.mcgill.ecse321group1.gamestore.dto;

import ca.mcgill.ecse321group1.gamestore.model.Category;

public class CategoryResponseDto {
    private int id;
    private String name;
    private String description;

    public CategoryResponseDto(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }
    
    // This is needed for tests to pass but I'm not sure why.
    protected CategoryResponseDto() {
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }
}
