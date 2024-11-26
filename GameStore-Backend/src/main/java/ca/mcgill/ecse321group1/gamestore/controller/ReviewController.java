package ca.mcgill.ecse321group1.gamestore.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321group1.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.service.CustomerService;
import ca.mcgill.ecse321group1.gamestore.service.ReviewService;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;
import org.springframework.web.bind.annotation.RequestBody;
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
     * @param request The review to create.
     * @return The created review, including the ID.
     */
    @PostMapping("/review")
    public ReviewResponseDto createReview(@Valid @RequestBody ReviewRequestDto request) {
        VideoGame gotVideoGame = videoGameService.getVideoGame(request.getVideoGameId());
        Customer gotCustomer = customerService.getCustomer(request.getCustomerId());
        Review createdReview = reviewService.createReview(request.getContent(), Date.valueOf(request.getDate()), Review.Rating.valueOf(request.getRating()), gotVideoGame, gotCustomer);
        return new ReviewResponseDto(createdReview);
            }
        
    /**
     * Return review by ID.
     * 
     * @param rid The ID of the review you want to return
     * @return The review with matching ID
     */
    @GetMapping("/review/{rid}")
    public ReviewResponseDto getReview(@PathVariable int rid) {
        Review gotReview = reviewService.getReview(rid);
        return new ReviewResponseDto(gotReview);
    }

    /**
     * Edit review by ID
     * 
     * @param rid The ID of the review you want to edit
     * @param request The primary key (review ID) of the review to edit.
     * @return The review with edited attributes
     */
    @PutMapping("/review/{rid}")
    public ReviewResponseDto editReviewById(@PathVariable int rid, @Valid @RequestBody ReviewRequestDto request) {
        Review editedReview = reviewService.editReview(rid, request.getContent(), Date.valueOf(request.getDate()), Review.Rating.valueOf(request.getRating()));
        return new ReviewResponseDto(editedReview);
    }
}
