package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.Reply;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.model.Category;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

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
        replyRepository.deleteAll();
        reviewRepository.deleteAll();
        videoGameRepository.deleteAll();
        customerRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadReply() {
        // Step 1: Create and save a category
        Category category = new Category("Action", "Action games");
        category = categoryRepository.save(category);

        // Step 2: Create and save a video game
        VideoGame videoGame = new VideoGame("Halo", "A popular FPS game", "59.99", "100", "2024-10-10", VideoGame.Status.Active, category);
        videoGame = videoGameRepository.save(videoGame);

        // Step 3: Create and save a customer
        Customer customer = new Customer("GameFan", "gamefan@example.com", "password123", "789 Gaming Blvd", "1231231234");
        customer = customerRepository.save(customer);

        // Step 4: Create and save a review
        Review review = new Review("Awesome game!", "2024-10-11", "5 stars", videoGame, customer);
        review = reviewRepository.save(review);

        // Step 5: Create and save a reply
        Reply reply = new Reply("Thank you for your review!", "2024-10-12", review);
        reply = replyRepository.save(reply);

        // Assert that the reply was saved and loaded correctly
        Reply loadedReply = replyRepository.findReplyById(reply.getId());
        assertNotNull(loadedReply);
        assertEquals("Thank you for your review!", loadedReply.getContent());
        assertEquals("2024-10-12", loadedReply.getDate());
        assertEquals(review.getId(), loadedReply.getReview().getId());
    }
}