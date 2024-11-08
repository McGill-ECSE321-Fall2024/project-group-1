package ca.mcgill.ecse321group1.gamestore.dto;

import java.sql.Date;

import ca.mcgill.ecse321group1.gamestore.model.VideoGame;

// Okay I realize now we might need to separate this DTO into request and response versions.
public class VideoGameDto {
    public enum Status { Pending, Active, Inactive }

    private int id;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private Date date;
   // private Status status;

    public VideoGameDto(VideoGame videoGame) {
        this.id = videoGame.getId();
        this.name = videoGame.getName();
        this.description = videoGame.getDescription();
        this.price = videoGame.getPrice();
        this.quantity = videoGame.getQuantity();
        this.date = videoGame.getDate();
        // this.status = videoGame.getStatus();
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
/*
    public status getStatus() {
        return status;
    }
        */
}
