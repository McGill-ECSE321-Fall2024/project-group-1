package ca.mcgill.ecse321group1.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.ReviewRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import ca.mcgill.ecse321group1.gamestore.model.Category;

import java.sql.Date;
import java.util.*;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void clearDatabase() {
        reviewRepository.deleteAll();
        videoGameRepository.deleteAll();
        customerRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadReview() {
        // Create and Save Category
        String name = "Action";
        String description = "Focuses on physical challenges";
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category = categoryRepository.save(category);

        // Create and Save Video Game
        String videoGameName = "Rainbow Six Siege";
        String videoGameDescription = "5v5 Shooter Game";
        Float price = 29.99f;
        Integer quantity = 5;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.FEBRUARY, 1);
        Date videoGameDate = new java.sql.Date(calendar.getTimeInMillis());
        VideoGame.Status status = VideoGame.Status.Active;

        VideoGame videoGame = new VideoGame();
        videoGame.setName(videoGameName);
        videoGame.setDescription(videoGameDescription);
        videoGame.setPrice(price);
        videoGame.setQuantity(quantity);
        videoGame.setDate(videoGameDate);
        videoGame.setStatus(status);
        videoGame.setCategory(category);
        videoGame = videoGameRepository.save(videoGame);

        // Create and Save Customer
        String username = "AlanBrotherton";
        String email = "alanbrotherton@example.com";
        String passwordHash = "password123";
        String address = "1600 Pennsylvania Avenue NW";
        String phoneNumber = "4703237795";
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPasswordHash(passwordHash);
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);
        customer = customerRepository.save(customer);

        // Create and Save Review
        String content = "Amazing game, 10/10 would recommend";
        Calendar reviewCalendar = Calendar.getInstance();
        reviewCalendar.set(2024, Calendar.MARCH, 1);
        Date reviewDate = new java.sql.Date(reviewCalendar.getTimeInMillis());
        Review.Rating rating = Review.Rating.threeStar;

        Review review = new Review();
        review.setContent(content);
        review.setDate(reviewDate);
        review.setRating(rating);
        review.setReviewed(videoGame);
        review.setReviewer(customer);
        review = reviewRepository.save(review);

        // Read review from database
        int id = review.getId();
        Review reviewFromDb = reviewRepository.findReviewById(id);

        // Assert correct response
        assertNotNull(reviewFromDb);
        assertEquals(content, reviewFromDb.getContent());
        assertEquals(reviewDate.toLocalDate(), reviewFromDb.getDate().toLocalDate());
        assertEquals(rating, reviewFromDb.getRating());
        assertEquals(videoGame.getId(), reviewFromDb.getReviewed().getId());
        assertEquals(customer.getId(), reviewFromDb.getReviewer().getId());
    }
}
