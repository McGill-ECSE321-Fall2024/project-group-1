package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321group1.gamestore.model.Reply;
import ca.mcgill.ecse321group1.gamestore.model.Review;

public class ReviewResponseDto {
    private int id;
    private String content;
    private LocalDate date;
    private String rating;
    private int videoGameId;
    private int customerId;
    private Reply reply; // Might need to change this to DTO

    protected ReviewResponseDto() {
    }

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.date = review.getDate().toLocalDate();
        this.rating = review.getRating().toString();
        this.videoGameId = review.getReviewed().getId();
        this.customerId = review.getReviewer().getId();
        this.reply = review.getReply();
    }

    public int getId() {
        return id;
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

    public int getVideoGame() {
        return videoGameId;
    }

    public int getCustomer() {
        return customerId;
    }

    public Reply getReply() {
        return reply;
    }
}
