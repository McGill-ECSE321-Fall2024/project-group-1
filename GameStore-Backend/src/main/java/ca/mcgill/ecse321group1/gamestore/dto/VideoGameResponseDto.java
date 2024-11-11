package ca.mcgill.ecse321group1.gamestore.dto;

import java.sql.Date;

import ca.mcgill.ecse321group1.gamestore.model.VideoGame;

// This is what we want to return when application wants to get a Video Game based off ID
public class VideoGameResponseDto {
    private int id;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private Date date;
    private String status;

    protected VideoGameResponseDto() {
        
    }
    
    public VideoGameResponseDto(VideoGame videoGame) {
        // Do we need to return ID here even though they use the ID to request it? YES!
        this.id = videoGame.getId();
        this.name = videoGame.getName();
        this.description = videoGame.getDescription();
        this.price = videoGame.getPrice();
        this.quantity = videoGame.getQuantity();
        this.date = videoGame.getDate();
        this.status = videoGame.getStatus().toString(); // Not sure if this is proper; temporary workaround since the status enum is not the same if we use it here and we would have to convert the object. I think we handled this incorrectly in the project.
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

    public int getId() {
        return id;
    }
}
