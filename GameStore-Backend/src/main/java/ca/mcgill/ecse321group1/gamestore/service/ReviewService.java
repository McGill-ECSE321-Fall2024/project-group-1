package ca.mcgill.ecse321group1.gamestore.service;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repo;

    /**Retrieves a Review object based on lookup by id*/
    @Transactional
    public Review getReview(int cid) {
        Review c = repo.findReviewById(cid);
        if (c == null) {
            throw new IllegalArgumentException("There is no owner with ID " + cid + ".");
        }
        return c;
    }

    /**Creates a new Review object with given attributes, and stores it into the databases*/
    @Transactional
    public Review createReview(String content, Date date, Review.Rating rating, VideoGame game, Customer customer) {
        if (game == null)
            throw new IllegalArgumentException("Review must belong to a game!");
        if (customer == null)
            throw new IllegalArgumentException("Review must have been written by a customer!");
        if (rating == null)
            throw new IllegalArgumentException("Review must have Rating!");

        Review review = new Review();//content date rating videogame customer
        review.setContent(content);
        review.setDate(date);
        review.setRating(rating);
        review.setReviewed(game);
        review.setReviewer(customer);
        return repo.save(review);//no ID, gets set by this.
    }

    /**Retrieves a Review by ID, and modifies its attributes according to editReview's arguments before re-storing*/
    @Transactional
    public Review editReview(int id, String content, Date date, Review.Rating rating) {
        Review review = repo.findById(id).orElse(null);

        if (review == null)
            throw new IllegalArgumentException(id + " does not correspond to an extant Review!");

        review.setContent(content);
        review.setDate(date);
        review.setRating(rating);
        return repo.save(review);//already has an ID
    }
    /**Deletes an Review, permanently.*/
    @Transactional
    public void deleteReview(int id) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant Review!");
        repo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }
}