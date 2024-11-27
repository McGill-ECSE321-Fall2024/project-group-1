package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321group1.gamestore.model.Reply;

public class ReplyResponseDto {
    private int replyId;
    private String content;
    private LocalDate date;
    private int reviewId;
    
    public ReplyResponseDto(Reply reply) {
        replyId = reply.getId();
        content = reply.getContent();
        date = reply.getDate().toLocalDate();
        reviewId = reply.getReview().getId();

    }

    public int getId() {
        return replyId;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getReviewId() {
        return reviewId;
    }

    protected ReplyResponseDto() {
    }
    
}
