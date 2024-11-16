package ca.mcgill.ecse321group1.gamestore.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321group1.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.service.CustomerService;
import ca.mcgill.ecse321group1.gamestore.service.ReviewService;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private VideoGameService videoGameService;
    @Autowired
    private CustomerService customerService;

    /**
     * Create new review.
     * 
     * @param reviewToCreate The review to create.
     * @return The created review, including the ID.
     */
    @PostMapping("/videogame/review/")
    public ReviewResponseDto createReview(@Valid @RequestBody ReviewRequestDto request) {
        VideoGame gotVideoGame = videoGameService.getVideoGame(request.getVideoGameId());
        Customer gotCustomer = customerService.getCustomer(request.getCustomerId());
        Review createdReview = reviewService.createReview(request.getContent(), Date.valueOf(request.getDate()), Review.Rating.valueOf(request.getRating()), gotVideoGame, gotCustomer);
        return new ReviewResponseDto(createdReview);
    }
    
    
}
