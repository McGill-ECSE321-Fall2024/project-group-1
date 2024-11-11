package ca.mcgill.ecse321group1.gamestore.dto;

import java.sql.Date;
import jakarta.validation.constraints.NotBlank;

import ca.mcgill.ecse321group1.gamestore.model.Category;


// This is what we we need 
public class VideoGameRequestDto {
    /* This might need to be split into new Dto
    private int id; 

    public VideoGameRequestDto(int id) {
        this.id = id;
    }
    */
//String name, String description, float price, int quantity, Date date, Category category
    @NotBlank(message = "Video game name is required.")
    private String name;
    @NotBlank(message = "Video game description is required.")
    private String description;
    @NotBlank(message = "Video game price is required.")
    private float price;
    private int quantity;
    private Date date;
    private Category category;
    public VideoGameRequestDto(String name, String description, float price, int quantity, Date date, Category category) {

    }
}
