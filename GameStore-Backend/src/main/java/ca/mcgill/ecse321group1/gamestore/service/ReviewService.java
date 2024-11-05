package ca.mcgill.ecse321group1.gamestore.service;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.Reply;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
            throw new IllegalArgumentException("There is no review with ID " + cid + ".");
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
        review.setReviewer(customer);
        review.setReviewed(game); //TODO: here is an issue. It returns false. But internally it returns true. It's like between the return and here it is changed to false. ???
        review.setReply(null);
        return review;
    }

    /**Links a Reply to a Review*/
    @Transactional
    public Review createReply(int review_id, String content, Date date, ReplyService replyService) {
        Review review = getReview(review_id);
        Reply reply = replyService.initReply(content, date, review);
        review.setReply(reply);
        return repo.save(review);
    }

    /**Removes a Reply from a Review, silently.*/
    @Transactional
    public Review removeReply(int review_id, ReplyService replyService) {
        Review review = getReview(review_id);
        Reply reply = review.getReply();
        if (reply != null) replyService.deleteReply(reply.getId());
        review.setReply(null);
        return repo.save(review);
    }

    /**Retrieves a Review by ID, and modifies its attributes according to editReview's arguments before re-storing. Cannot change reviewer or reviewed game.*/
    @Transactional
    public Review editReview(int id, String content, Date date, Review.Rating rating) {
        Review review = getReview(id);

        if (rating == null)
            throw new IllegalArgumentException("Review must have Rating!");

        review.setContent(content);
        review.setDate(date);
        review.setRating(rating);
        return repo.save(review);//already has an ID
    }
    /**Deletes a Review, permanently.*/
    @Transactional
    public void deleteReview(int id, ReplyService replyService) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant Review!");
        Review review = getReview(id);
        if(review.getReply() != null) replyService.deleteReply(review.getReply().getId());
        repo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }
}