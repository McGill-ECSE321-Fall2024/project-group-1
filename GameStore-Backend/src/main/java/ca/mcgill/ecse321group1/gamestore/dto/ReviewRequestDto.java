package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321group1.gamestore.model.Review;

public class ReviewRequestDto {
    private String content;
    private LocalDate date;
    private String rating;
    private VideoGameResponseDto videoGame;
    private PersonResponseDto customer;

    public ReviewRequestDto(String content, LocalDate date, Review.Rating rating, VideoGameResponseDto videoGame, PersonResponseDto customer){
        this.content = content;
        this.date = date;
        this.rating = rating.toString(); // Hoping this works. 
        this.videoGame = videoGame;
        this.customer = customer;
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

    public VideoGameResponseDto getVideoGame(){
        return videoGame;
    }

    public PersonResponseDto getCustomer() {
        return customer;
    }
}
