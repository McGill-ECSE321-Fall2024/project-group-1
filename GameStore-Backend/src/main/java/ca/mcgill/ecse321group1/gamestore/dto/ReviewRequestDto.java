package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321group1.gamestore.model.Review;

public class ReviewRequestDto {
    private String content;
    private LocalDate date;
    private String rating;
    private int videoGameId;
    private int customerId;

    public ReviewRequestDto(String content, LocalDate date, Review.Rating rating, int videoGameId, int customerId){
        this.content = content;
        this.date = date;
        this.rating = rating.toString(); // Hoping this works. 
        this.videoGameId = videoGameId;
        this.customerId = customerId;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getRating() {
        return rating;
    }

    public int getVideoGameId(){
        return videoGameId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
