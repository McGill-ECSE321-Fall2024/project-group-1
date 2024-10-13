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

import java.sql.Date;
import java.util.*;

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
        // Create and Save Category
        String name = "Action";
        String description = "Focuses on physical challenges";
        Category category = new Category(name, description);
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
        VideoGame videoGame = new VideoGame(videoGameName, videoGameDescription, price, quantity, videoGameDate, status, category);
        videoGame = videoGameRepository.save(videoGame);

        // Create and Save Customer
        String username = "AlanBrotherton";
        String email = "alanbrotherton@example.com";
        String passwordHash = "password123";
        String address = "1600 Pennsylvania Avenue NW";
        String phoneNumber = "4703237795";
        Customer customer = new Customer(username, email, passwordHash, address, phoneNumber);
        customer = customerRepository.save(customer);

        // Create and Save Review
        String content = "Amazing game, 10/10 would reccomend";
        Calendar reviewCalendar = Calendar.getInstance();
        reviewCalendar.set(2024, Calendar.MARCH, 1);
        Date reviewDate = new java.sql.Date(calendar.getTimeInMillis());
        Review.Rating rating = Review.Rating.fourStar;
        Review review = new Review(content, reviewDate, rating, videoGame, customer);
        review = reviewRepository.save(review);

        // Create and Save Reply
        String replyContent = "I am glad you enjoyed the game!";
        Calendar replyCalendar = Calendar.getInstance();
        replyCalendar.set(2024, Calendar.APRIL, 1);
        Date replyDate = new java.sql.Date(calendar.getTimeInMillis());
        Reply reply = new Reply(replyContent, replyDate, review);
        reply = replyRepository.save(reply);

        // Read owner from database
        int id = reply.getId();
        Reply replyFromDb = replyRepository.findReplyById(id);

        // Assert correct response
        assertNotNull(replyFromDb);
        assertEquals(replyFromDb.getContent(), replyContent);
        assertEquals(replyFromDb.getDate().toLocalDate(), replyDate.toLocalDate());
        assertEquals(replyFromDb.getReview().getId(), review.getId());
    }
}