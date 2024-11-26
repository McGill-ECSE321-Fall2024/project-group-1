package ca.mcgill.ecse321group1.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;

import ca.mcgill.ecse321group1.gamestore.model.*;
import ca.mcgill.ecse321group1.gamestore.repository.*;
import ca.mcgill.ecse321group1.gamestore.service.ReplyService;
import ca.mcgill.ecse321group1.gamestore.service.ReviewService;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReplyServiceTests {
    @Mock
    private ReplyRepository repo;
    @Mock
    private ReviewRepository revrepo;
    @Mock
    private CategoryRepository catrepo;
    @Mock
    private VideoGameRepository gamerepo;
    @Mock
    private CustomerRepository custrepo;
    @InjectMocks
    private ReplyService service;

    private Reply backhand;
    private Review scathing;
    private VideoGame game;
    private Customer BOB;

    @BeforeEach
    public void preparationReply() {
        final String[] fields = new String[]{"BobWilson", "Bobby@gmail.com", "Password123", "123 Mason Road, USA", "+2 248 893 524"};
        BOB = new Customer(17, fields[0], fields[1], PersonServiceTestHelper.hash_password(fields[2]), fields[3], fields[4]);
        when(custrepo.save(any(Customer.class))).thenReturn(BOB);
        BOB = custrepo.save(BOB);

        Category cat = new Category();
        cat.setName("Questing");
        cat.setDescription("Knights and stuff");
        cat.setId(12312);
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
        game.setId(123132);
        when(gamerepo.save(game)).thenReturn(game);
        game = gamerepo.save(game);

        scathing = new Review(182, "Great game! 5/5", new Date(10000000000000L), Review.Rating.fourStar, game, BOB);
        when(revrepo.save(scathing)).thenReturn(scathing);
        scathing = revrepo.save(scathing);

        backhand = new Reply(12, "You are completely wrong!", new Date(188232321), scathing);
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateValidReview() {
        // Arrange
        when(repo.save(any(Reply.class))).thenReturn(backhand);
        when(revrepo.findReviewById(182)).thenReturn(scathing);

        // Act
        Reply createdReply = service.createReply("You are completely wrong!", new Date(188232321), scathing);
        // Assert
        assertNotNull(createdReply);
        assertEquals("You are completely wrong!", createdReply.getContent());
        assertEquals(new Date(188232321), createdReply.getDate());
        assertEquals(scathing, createdReply.getReview());
        verify(repo, times(1)).save(createdReply);//saved once, on creation
    }

    @Test
    public void testReadReviewByValidId() {
        // Arrange
        int id = 42;

        when(repo.findReplyById(id)).thenReturn(backhand);

        // Act
        Reply retrieved = service.getReply(id);

        // Assert
        assertNotNull(retrieved);
        assertEquals("You are completely wrong!", retrieved.getContent());
        assertEquals((new Date(188232321)).toString(), retrieved.getDate().toString());
        assertEquals(scathing, retrieved.getReview());
    }

    @Test
    public void testReadReviewByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findReplyById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getReply(id));
        assertEquals("There is no reply with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteReplyByValidID() {
        // Arrange
        int id = 182, revId = 190;
        backhand.setId(id);
        scathing.setId(revId);
        when(repo.save(any(Reply.class))).thenReturn(backhand);
        when(revrepo.findReviewById(revId)).thenReturn(scathing);
        Reply createdReply = service.createReply("You are completely wrong!", new Date(188232321), scathing);

        when(repo.findReplyById(id)).thenReturn(backhand);
        when(repo.existsById(id)).thenReturn(true);
        when(revrepo.findReviewById(revId)).thenReturn(scathing);

        //when(repo.findCategoryById(id)).thenReturn();

        // Act
        service.deleteReply(id);

        // Assert
        assertEquals(scathing, createdReply.getReview());//review template - reply VS removed reply review
        //IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getReply(id));
        //assertEquals("There is no reply with ID " + id + ".", e.getMessage()); //THIS CANNOT BE TESTED HERE due to the twisted and evil nature of mockito, ie we are the ones TELLING it that getReply(id) returns backhand, by definition
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteReviewByInvalidID() {
        // Arrange
        int id = 42, revId = 12;
        scathing.setId(revId);
        backhand.setId(id);
        scathing = revrepo.save(scathing);
        //backhand.setId(42);
        when(repo.findReplyById(id)).thenReturn(null);
        when(revrepo.findReviewById(revId)).thenReturn(scathing);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteReply(id));
        assertEquals(id + " cannot be deleted as it does not correspond to an extant Reply!", e.getMessage());
    }

    @Test
    public void testEditValidReview() {
        Date date2 = new Date(123123);
        String content2 = "Nevermind. I played the game. You were right.";
        //Arrange
        int id = 182, revId = 1293;
        scathing.setId(revId);
        backhand.setId(id);
        when(revrepo.findReviewById(revId)).thenReturn(scathing);
        when(repo.findReplyById(id)).thenReturn(backhand);
        when(repo.save(any(Reply.class))).thenReturn(backhand);
        when(revrepo.save(any(Review.class))).thenReturn(scathing);

        // Act
        Reply createdReply = service.createReply("You are completely wrong!", new Date(188232321), scathing);
        service.editReply(id, content2, date2);

        // Assert
        assertNotNull(createdReply);
        assertEquals(content2, createdReply.getContent());
        assertEquals(date2, createdReply.getDate());
        assertEquals(scathing, createdReply.getReview());
        verify(repo, times(1)).save(createdReply);//saved once, on creation
    }
}