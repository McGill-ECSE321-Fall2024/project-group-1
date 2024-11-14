package ca.mcgill.ecse321group1.gamestore.dto;

public class CategoryRequestDto {
    private String name;
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
