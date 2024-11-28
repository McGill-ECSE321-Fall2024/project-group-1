package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321group1.gamestore.model.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestDto {
    @NotBlank(message = "Review must not be blank!")
    private String content;
    @NotNull(message = "Review date must exist!")
    private LocalDate date;
    @NotBlank(message = "Review rating must not be blank!")
    private String rating;
    private int videoGameId;
    private int customerId;

    public ReviewRequestDto(String content, LocalDate date, Review.Rating rating, int videoGameId, int customerId){
        this.content = content;
        this.date = date;
        this.rating = rating.toString(); 
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
