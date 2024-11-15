package ca.mcgill.ecse321group1.gamestore.dto;

import java.sql.Date;


import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.model.Category;


// This is what we want to return when application wants to get a Video Game based off ID
public class VideoGameResponseDto {
    private int id;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private Date date;
    private String status;
    private Category category;

    protected VideoGameResponseDto() {
    }
    
    public VideoGameResponseDto(VideoGame videoGame) {
        this.id = videoGame.getId();
        this.name = videoGame.getName();
        this.description = videoGame.getDescription();
        this.price = videoGame.getPrice();
        this.quantity = videoGame.getQuantity();
        this.date = videoGame.getDate();
        this.status = videoGame.getStatus().toString();
        this.category = videoGame.getCategory();
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

    public String getStatus() {
        return status;
    }

    public Category getCategory() {
        return category;
    }
}
