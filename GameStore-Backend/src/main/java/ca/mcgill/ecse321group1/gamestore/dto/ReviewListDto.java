package ca.mcgill.ecse321group1.gamestore.dto;

import java.util.List;

public class ReviewListDto {
    private List<ReviewResponseDto> reviews;

    public List<ReviewResponseDto> getReviews() {
        return reviews;
    }

    public ReviewListDto(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }
    
    // For Jackson
    protected ReviewListDto() {

    }
    
}
