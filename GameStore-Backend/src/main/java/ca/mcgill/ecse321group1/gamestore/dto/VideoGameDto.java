package ca.mcgill.ecse321group1.gamestore.dto;

import java.sql.Date;
import jakarta.validation.constraints.NotBlank;

import ca.mcgill.ecse321group1.gamestore.model.VideoGame;

// Okay I realize now we might need to separate this DTO into request and response versions.
public class VideoGameDto {
    private int id;
    @NotBlank(message = "Video game name is required.")
    private String name;
    private String description;
    private float price;
    private int quantity;
    private Date date;
    private int status;

    public VideoGameDto(VideoGame videoGame) {
        this.id = videoGame.getId();
        this.name = videoGame.getName();
        this.description = videoGame.getDescription();
        this.price = videoGame.getPrice();
        this.quantity = videoGame.getQuantity();
        this.date = videoGame.getDate();
        this.status = videoGame.getStatus().ordinal();
    }

    public VideoGameDto(int id, String name, String description, float price, int quantity, Date date, int status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
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

    public int getStatus() {
        return status;
    }
        
}
