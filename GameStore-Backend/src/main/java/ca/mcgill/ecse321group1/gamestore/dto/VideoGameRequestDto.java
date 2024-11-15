package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ca.mcgill.ecse321group1.gamestore.model.Category;


public class VideoGameRequestDto {
    private String name;
    private String description;
    private float price;
    private int quantity;
    private LocalDate date;
    private int categoryId;

    public VideoGameRequestDto(String name, String description, float price, int quantity, LocalDate date, int categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.categoryId = categoryId;
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

    public LocalDate getDate() {
        return date;
    }

    public int getCategoryId() {
        return categoryId;
    }
}


