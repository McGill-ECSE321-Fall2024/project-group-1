package ca.mcgill.ecse321group1.gamestore.dto;

import java.sql.Date;
import jakarta.validation.constraints.NotBlank;

import ca.mcgill.ecse321group1.gamestore.model.Category;


// This is what we we need 
public class VideoGameRequestDto {
    /* This might need to be split into a new Dto
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
    @NotBlank(message = "Video game quantity is required.")
    private int quantity;
    @NotBlank(message = "Video game date is required")
    private Date date;
    @NotBlank(message = "Video game category is required")
    private Category category;

    public VideoGameRequestDto(String name, String description, float price, int quantity, Date date, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }
}


