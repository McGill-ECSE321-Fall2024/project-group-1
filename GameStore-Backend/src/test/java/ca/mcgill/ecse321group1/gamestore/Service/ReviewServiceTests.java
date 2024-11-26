package ca.mcgill.ecse321group1.gamestore.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.*;
import ca.mcgill.ecse321group1.gamestore.service.ReplyService;
import ca.mcgill.ecse321group1.gamestore.service.ReviewService;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;
import ca.mcgill.ecse321group1.gamestore.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewServiceTests {

    @Mock
    private ReviewRepository repo;
    @Mock
    private CategoryRepository catrepo;
    @Mock
    private VideoGameRepository gamerepo;
    @Mock
    private CustomerRepository custrepo;
    @InjectMocks
    private ReviewService service;
    @InjectMocks
    private ReplyService replyservice;

    private Review scathing;
    private VideoGame game;
    private Customer BOB;
    private Category cat;

    @BeforeEach
    public void preparationReview() {
        final String[] fields = new String[]{"BobWilson", "Bobby@gmail.com", "Password123", "123 Mason Road, USA", "+2 248 893 524"};
        BOB = new Customer(17, fields[0], fields[1], PersonServiceTestHelper.hash_password(fields[2]), fields[3], fields[4]);
        when(custrepo.save(BOB)).thenReturn(BOB);
        BOB = custrepo.save(BOB);

        cat = new Category();
        cat.setName("Questing");
        cat.setDescription("Knights and stuff");
        cat.setId(12);
        when(catrepo.save(cat)).thenReturn(cat);
        cat = catrepo.save(cat);


        game = new VideoGame();
        game.setName("Far Cry XLI");
        game.setDescription("Far Cry XL II — XLI");
        game.setCategory(cat);
        game.setDate(new Date(100000000000L));
        game.setPrice(1.2F);
        game.setQuantity(10002);
        game.setStatus(VideoGame.Status.Active);
        game.setId(123);
        when(gamerepo.save(game)).thenReturn(game);
        game = gamerepo.save(game);

        scathing = new Review(182, "Great game! 5/5", new Date(10000000000000L), Review.Rating.fourStar, game, BOB);
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateValidReview() {
        // Arrange
        when(repo.save(scathing)).thenReturn(scathing);


        // Act
        Review createdReview = service.createReview("Great game! 5/5", new Date(10000000000000L), Review.Rating.fourStar, game, BOB);

        // Assert
        assertNotNull(createdReview);
        assertEquals("Great game! 5/5", createdReview.getContent());
        assertEquals(new Date(10000000000000L), createdReview.getDate());
        assertEquals(Review.Rating.fourStar, createdReview.getRating());
        assertEquals(game, createdReview.getReviewed()); //FAILING: issue inside createReview / Review.setReviewed() <- umple generated method is CURSED....
        assertEquals(BOB, createdReview.getReviewer());
        verify(repo, times(1)).save(scathing);//saved once, on creation
    }

    @Test
    public void testReadReviewByValidId() {
        // Arrange
        int id = 42;

        when(repo.findReviewById(id)).thenReturn(scathing);

        // Act
        Review retrieved = service.getReview(id);

        // Assert
        assertNotNull(retrieved);
        assertEquals(scathing.getContent(), retrieved.getContent());
        assertEquals(scathing.getDate().toString(), retrieved.getDate().toString());
        assertEquals(scathing.getRating(), retrieved.getRating());
        assertEquals(scathing.getReviewed(), retrieved.getReviewed());
        assertEquals(scathing.getReviewer(), retrieved.getReviewer());
    }

    @Test
    public void testReadReviewByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findReviewById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getReview(id));
        assertEquals("There is no review with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteReviewByValidID() {
        // Arrange
        int id = 12;
        when(repo.findReviewById(id)).thenReturn(scathing);
        when(repo.existsById(id)).thenReturn(true);
        when(service.getReview(id)).thenReturn(scathing);

        //when(repo.findCategoryById(id)).thenReturn();

        // Act
        service.deleteReview(id, replyservice);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteReviewByInvalidID() {
        // Arrange
        int id = 42;
        when(repo.existsById(id)).thenReturn(false);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteReview(id, replyservice));
        assertEquals(id + " cannot be deleted as it does not correspond to an extant Review!", e.getMessage());
    }

    @Test
    public void testCreateInvalidGamelessReview() {
        // Arrange

        // Act
        when(repo.save(scathing)).thenReturn(scathing);
        Review createdReview = service.createReview("Great game! 5/5", new Date(10000000000000L), Review.Rating.fourStar, game, BOB);
        when(repo.findAll()).thenReturn(List.of(createdReview));
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.createReview("Great game! 5/5", new Date(10000000000000L), Review.Rating.fourStar, game, BOB));
        assertEquals("Customer has already made a review for this game!", e.getMessage());
    }

    @Test
    public void testCreateInvalidSecondReview() {
        // Arrange

        // Act

        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.createReview("Great game! 5/5", new Date(10000000000000L), Review.Rating.fourStar, null, BOB));
        assertEquals("Review must belong to a game!", e.getMessage());
    }

    @Test
    public void testEditValidReview() {
        //Arrange
        int id = 12; // Set a unique ID for the category
        String content2 = "HATED THIS GAME";
        Review.Rating rating2 = Review.Rating.oneStar;

        // Mock behavior of repository
        when(repo.findReviewById(id)).thenReturn(scathing);
        when(repo.save(any(Review.class))).thenReturn(scathing);

        // Act
        Review edited = service.editReview(id, content2, new Date(12321), rating2);

        // Assert
        assertNotNull(edited);
        assertEquals(content2, edited.getContent());
        assertEquals(rating2, edited.getRating());
        verify(repo, times(1)).save(scathing);
    }

    @Test
    public void testEditReviewInvalidDescription() {
        //Arrange
        int id = 12; // Set a unique ID for the category

        // Mock behavior of repository
        when(repo.findReviewById(id)).thenReturn(scathing);
        when(repo.save(any(Review.class))).thenReturn(scathing);

        // Act

        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.editReview(id, "RANDOM", new Date(192312), null));
        assertEquals("Review must have Rating!", e.getMessage());
    }

    @Test
    public void testGetAllReviewsVideoGame () {
        final String[] fields = new String[]{"BobWilson", "Bobby@gmail.com", "Password123", "123 Mason Road, USA", "+2 248 893 524"};
        Customer NotBob = new Customer(19, "NotBob", "Weird@mail.com.gmail.org", PersonServiceTestHelper.hash_password(fields[2]), fields[3], fields[4]);
        when(custrepo.save(NotBob)).thenReturn(NotBob);
        NotBob = custrepo.save(NotBob);

        Review one = new Review(12, "asdf", new Date(0), Review.Rating.threeStar, game, BOB);
        when(repo.save(one)).thenReturn(one);
        one = repo.save(one);

        VideoGame game2 = new VideoGame();
        game2.setName("Far Cry XLI");
        game2.setDescription("Far Cry XL II — XLI");
        game2.setCategory(cat);
        game2.setDate(new Date(100000000000L));
        game2.setPrice(1.2F);
        game2.setQuantity(10002);
        game2.setStatus(VideoGame.Status.Active);
        game2.setId(1232);
        when(gamerepo.save(game2)).thenReturn(game2);
        game2 = gamerepo.save(game2);

        Review two = new Review(12, "actual review", new Date(0), Review.Rating.fiveStar, game2, NotBob);
        when(repo.save(two)).thenReturn(two);
        two = repo.save(two);
        when(repo.findAll()).thenReturn(List.of(one, two));
        //Act
        List<Review> output = service.getAllReviews(game.getId());

        //Assert
        assertEquals(1, output.size());
        assertTrue(output.contains(one));
    }
}