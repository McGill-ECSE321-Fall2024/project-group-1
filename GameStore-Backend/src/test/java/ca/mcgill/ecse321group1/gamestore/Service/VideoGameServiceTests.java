package ca.mcgill.ecse321group1.gamestore.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.*;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.*;
import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VideoGameServiceTests {
    @Mock
    private VideoGameRepository repo;
    @Mock
    private CustomerRepository custrepo;
    @Mock
    private ReviewRepository revrepo;
    @Mock
    private CategoryRepository catrepo;
    @InjectMocks
    private VideoGameService service;


    private final int ID = 31;
    private final VideoGame[] games = new VideoGame[3];
    Category action, dating;

    @BeforeEach
    public void preparationVideoGame() {
        action = new Category();
        action.setName("Action");
        action.setDescription("Boom!");
        dating = new Category();
        dating.setName("Dating");
        dating.setDescription("MCQ w/o answer key");
        action.setId(12);
        dating.setId(13);
        when(catrepo.save(action)).thenReturn(action);
        when(catrepo.save(dating)).thenReturn(dating);
        action = catrepo.save(action);
        dating = catrepo.save(dating);

        games[0] = new VideoGame();
        games[0].setName("Digimon X");
        games[0].setDescription("Newest Digimon game!");
        games[0].setQuantity(12);
        games[0].setPrice(32.23f);
        games[0].setStatus(VideoGame.Status.Active);
        games[0].setCategory(action);
        games[0].setDate(new Date(10000));

        games[1] = new VideoGame();
        games[1].setName("Digimon XL");
        games[1].setDescription("Really The Newest Digimon game!");
        games[1].setQuantity(1);
        games[1].setPrice(362.23f);
        games[1].setStatus(VideoGame.Status.Pending);
        games[1].setCategory(action);
        games[1].setDate(new Date(100002));

        games[2] = new VideoGame();
        games[2].setName("Spore");
        games[2].setDescription("Evolve or die.");
        games[2].setQuantity(122);
        games[2].setPrice(312.23f);
        games[2].setStatus(VideoGame.Status.Pending);
        games[2].setCategory(dating);
        games[2].setDate(new Date(1000089));
    }


    @SuppressWarnings("null")
    @Test
    public void testCreateValidVideoGame() {
        // Arrange
        when(repo.save(any(VideoGame.class))).thenReturn(games[1]);

        // Act
        VideoGame createdGame = service.createVideoGame("Digimon XL", "Really The Newest Digimon game!", 362.23f, 1, new Date(100002), action);

        // Assert
        assertNotNull(createdGame);
        assertEquals("Digimon XL", createdGame.getName());
        assertEquals("Really The Newest Digimon game!", createdGame.getDescription());
        assertEquals(362.23f, createdGame.getPrice());
        assertEquals(1, createdGame.getQuantity());
        assertEquals((new Date(100002)).toString(), createdGame.getDate().toString());
        assertEquals(VideoGame.Status.Pending, createdGame.getStatus());
        assertEquals(action, createdGame.getCategory());
        verify(repo, times(1)).save(games[1]);//saved once, on creation issue with null
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateInvalidVideoGame() {
        // Arrange
        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                service.createVideoGame("Digimon XL", "Really The Newest Digimon game!", -362.23f, 1, new Date(100002), action));
        assertEquals("Price must be non-negative!", e.getMessage());
    }

    @Test
    public void testReadVideoGameByValidId() {
        // Arrange
        when(repo.findVideoGameById(ID)).thenReturn(games[1]);

        // Act
        VideoGame retrieved = service.getVideoGame(ID);

        // Assert
        assertNotNull(retrieved);
        assertEquals("Digimon XL", retrieved.getName());
        assertEquals("Really The Newest Digimon game!", retrieved.getDescription());
        assertEquals(362.23f, retrieved.getPrice());
        assertEquals(1, retrieved.getQuantity());
        assertEquals((new Date(100002)).toString(), retrieved.getDate().toString());
        assertEquals(VideoGame.Status.Pending, retrieved.getStatus());
        assertEquals(action, retrieved.getCategory());
    }

    @Test
    public void testReadVideoGameByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findVideoGameById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getVideoGame(id));
        assertEquals("There is no video game with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteVideoGameByValidID() {
        // Arrange
        int id = 12;
        when(repo.existsById(id)).thenReturn(true);
        //when(repo.findCategoryById(id)).thenReturn();

        // Act
        service.deleteVideoGame(id);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteVideoGameByInvalidID() {
        // Arrange
        int id = 42;
        when(repo.existsById(id)).thenReturn(false);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteVideoGame(id));
        assertEquals(id + " cannot be deleted as it does not correspond to an extant VideoGame!", e.getMessage());
    }

    @Test
    public void testEditValidVideoGame() {
        //Arrange
        String new_name = "DIGIMON CLX";
        String new_description = "Newly released ULTRA VERSION";


        // Mock behavior of repository
        when(repo.findVideoGameById(ID)).thenReturn(games[1]);
        when(repo.save(any(VideoGame.class))).thenReturn(games[1]);

        // Act
        VideoGame edited = service.editVideoGame(ID, new_name, new_description, 362.23f, new Date(1000123), VideoGame.Status.Pending, dating);

        // Assert
        assertNotNull(edited);
        assertEquals(new_name, edited.getName());
        assertEquals(new_description, edited.getDescription());
        assertEquals((new Date(1000123)).toString(), edited.getDate().toString());
        assertEquals(dating, edited.getCategory());
        verify(repo, times(1)).save(games[1]);
    }
    @Test
    public void testVideoGameAlterQuantity() {
        // Arrange
        when(repo.findVideoGameById(ID)).thenReturn(games[2]);
        when(repo.save(any(VideoGame.class))).thenReturn(games[2]);
        // Act
        VideoGame edited = service.alterQuantity(ID, 123);

        // Assert
        assertEquals(123 + 122, edited.getQuantity());
    }

    @Test
    public void testVideoGameDeactivate() {
        // Arrange
        when(repo.findVideoGameById(ID)).thenReturn(games[0]);
        when(repo.save(any(VideoGame.class))).thenReturn(games[0]);
        // Act
        VideoGame edited = service.deactivateGame(ID);

        // Assert
        assertEquals(VideoGame.Status.Inactive, edited.getStatus());
    }

    @Test
    public void testApproveVideoGame () {
        when(repo.findVideoGameById(ID)).thenReturn(games[1]);
        when(repo.save(any(VideoGame.class))).thenReturn(games[1]);
        // Act
        VideoGame edited = service.approveGame(ID);

        // Assert
        assertEquals(VideoGame.Status.Active, edited.getStatus());
    }

    @Test
    public void testApproveVideoGameNotPending () {
        when(repo.findVideoGameById(ID)).thenReturn(games[0]);
        when(repo.save(any(VideoGame.class))).thenReturn(games[0]);
        // Act

        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.approveGame(ID));
        assertEquals("VideoGame " + ID + " is not currently Pending!", e.getMessage());
    }
    @Test
    public void testSearchVideoGameByCategory () {
        when(repo.save(games[0])).thenReturn(games[0]);
        when(repo.save(games[1])).thenReturn(games[1]);
        when(repo.save(games[2])).thenReturn(games[2]);
        games[0] = repo.save(games[0]);
        games[1] = repo.save(games[1]);
        games[2] = repo.save(games[2]);
        // Act
        when(repo.findAll()).thenReturn(new ArrayList<>(List.of(games)));
        List<VideoGame> act = service.searchByCategory(action.getId());
        List<VideoGame> dat = service.searchByCategory(dating.getId());

        // Assert
        assertEquals(1, dat.size());
        assertEquals(2, act.size());
        assertTrue(dat.contains(games[2]));
        assertTrue(act.contains(games[1]));
        assertTrue(act.contains(games[0]));
    }

    @Test
    public void testSearchVideoGameByKeyword () {
        when(repo.save(games[0])).thenReturn(games[0]);
        when(repo.save(games[1])).thenReturn(games[1]);
        when(repo.save(games[2])).thenReturn(games[2]);
        games[0] = repo.save(games[0]);
        games[1] = repo.save(games[1]);
        games[2] = repo.save(games[2]);
        // Act
        when(repo.findAll()).thenReturn(new ArrayList<>(List.of(games)));
        List<VideoGame> digimon = service.searchByKeyword("Digimon");
        List<VideoGame> spore = service.searchByKeyword("evo");
        List<VideoGame> all = service.searchByKeyword("");

        // Assert
        assertEquals(1, spore.size());
        assertEquals(2, digimon.size());
        assertEquals(3, all.size());
        assertTrue(spore.contains(games[2]));
        assertTrue(digimon.contains(games[1]));
        assertTrue(digimon.contains(games[0]));
        for (int i = 0; i < 3; i++) assertTrue(all.contains(games[i]));
    }

    @Test
    public void testGetAllPending () {
        when(repo.save(games[0])).thenReturn(games[0]);
        when(repo.save(games[1])).thenReturn(games[1]);
        when(repo.save(games[2])).thenReturn(games[2]);
        games[0] = repo.save(games[0]);
        games[1] = repo.save(games[1]);
        games[2] = repo.save(games[2]);
        // Act
        when(repo.findAll()).thenReturn(new ArrayList<>(List.of(games)));
        List<VideoGame> pending = service.getAllPending();

        // Assert
        assertEquals(2, pending.size());
        assertTrue(pending.contains(games[2]));
        assertTrue(pending.contains(games[1]));
    }

    @Test
    public void testVideoGameGetAverageRating () {
        final String[] fields = new String[]{"BobWilson", "Bobby@gmail.com", "Password123", "123 Mason Road, USA", "+2 248 893 524"};
        Customer BOB = new Customer(17, fields[0], fields[1], PersonServiceTestHelper.hash_password(fields[2]), fields[3], fields[4]);
        when(custrepo.save(BOB)).thenReturn(BOB);
        BOB = custrepo.save(BOB);
        Customer NotBob = new Customer(19, "NotBob", "Weird@mail.com.gmail.org", PersonServiceTestHelper.hash_password(fields[2]), fields[3], fields[4]);
        when(custrepo.save(NotBob)).thenReturn(NotBob);
        NotBob = custrepo.save(NotBob);

        Review one = new Review(12, "asdf", new Date(0), Review.Rating.threeStar, games[0], BOB);
        when(revrepo.save(one)).thenReturn(one);
        one = revrepo.save(one);
        Review two = new Review(14, "actual review", new Date(0), Review.Rating.fiveStar, games[0], NotBob);
        when(revrepo.save(two)).thenReturn(two);
        two = revrepo.save(two);


        when(revrepo.findAll()).thenReturn(List.of(one, two));

        //Act
        Review.Rating rating = service.averageRatingOf(games[0].getId());

        //Assert
        assertEquals(Review.Rating.fourStar, rating);
    }
}