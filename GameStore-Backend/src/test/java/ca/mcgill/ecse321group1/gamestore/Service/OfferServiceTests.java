package ca.mcgill.ecse321group1.gamestore.Service;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.Offer;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OfferRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import ca.mcgill.ecse321group1.gamestore.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OfferServiceTests {
    @Mock
    private OfferRepository repo;
    @Mock
    private CategoryRepository catRepo;
    @Mock
    private VideoGameRepository gameRepo;
    @InjectMocks
    private OfferService service;

    private Offer offer;
    private String name = "MAY DAY MAYHEM", description = "May Day Invasion X HUGE DISCOUNTS", effect = "80%";
    private Date start = new Date(10120321), end = new Date(101203240);
    private VideoGame game;
    @BeforeEach
    public void setupOfferObject(){
        String cname = "ACTION";
        String cdescription = "Fighting, movement, violence!";
        Category action = new Category();
        action.setName(cname);
        action.setDescription(cdescription);
        action = catRepo.save(action);


        game = new VideoGame();
        game.setName("Far Cry XLI");
        game.setDescription("Far Cry XL II — XLI");
        game.setCategory(action);
        game.setDate(new Date(100000000000L));
        game.setPrice(1.2F);
        game.setQuantity(10002);
        game.setStatus(VideoGame.Status.Active);
        game = gameRepo.save(game);

        offer = new Offer();
        offer.setName(name);
        offer.setDescription(description);
        offer.setEffect(effect);
        offer.setStartDate(start);
        offer.setEndDate(end);
        offer.setVideoGame(game);
    }
    @SuppressWarnings("null")
    @Test
    public void testCreateValidOffer() {
        // Arrange

        when(repo.save(any(Offer.class))).thenReturn(offer);

        // Act
        Offer createdOffer = service.createOffer(name, description, effect, start, end, game);

        // Assert
        assertNotNull(createdOffer);
        assertEquals(name, createdOffer.getName());
        assertEquals(description, createdOffer.getDescription());
        assertEquals(effect, createdOffer.getEffect());
        assertEquals(start.toString(), createdOffer.getStartDate().toString());
        assertEquals(end.toString(), createdOffer.getEndDate().toString());
        assertEquals(game, createdOffer.getVideoGame());
        verify(repo, times(1)).save(offer);//saved once, on creation
    }

    @Test
    public void testReadOfferByValidId() {
        // Arrange
        int id = 42;
        when(repo.findOfferById(id)).thenReturn(offer);

        // Act
        Offer retrieved = service.getOffer(id);

        // Assert
        assertNotNull(retrieved);
        assertEquals(name, retrieved.getName());
        assertEquals(description, retrieved.getDescription());
        assertEquals(effect, retrieved.getEffect());
        assertEquals(start.toString(), retrieved.getStartDate().toString());
        assertEquals(end.toString(), retrieved.getEndDate().toString());
        assertEquals(game, retrieved.getVideoGame());
    }

    @Test
    public void testReadOfferByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findOfferById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getOffer(id));
        assertEquals("There is no offer with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteOfferByValidID() {
        // Arrange
        int id = 12;
        when(repo.existsById(id)).thenReturn(true);
        //when(repo.findCategoryById(id)).thenReturn();

        // Act
        service.deleteOffer(id);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteCategoryByInvalidID() {
        // Arrange
        int id = 42;
        when(repo.existsById(id)).thenReturn(false);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteOffer(id));
        assertEquals(id + " cannot be deleted as it does not correspond to an extant Offer!", e.getMessage());
    }

    @Test
    public void testCreateInvalidInvertedDateCategory() {
        // Arrange

        // Act

        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                service.createOffer(name, description, "2.22", start, new Date(0L), null));
        assertEquals("Start date must be before end date!", e.getMessage());
    }

    @Test
    public void testEditValidOffer() {
        //Arrange
        int id = 12; // Set a unique ID for the category
        String name2 = "Peace Day";
        String description2 = "Huge discount on Undertale";

        offer.setId(id); // Ensure the ID is set

        // Mock behavior of repository
        when(repo.findOfferById(id)).thenReturn(offer);
        when(repo.save(any(Offer.class))).thenReturn(offer);

        // Act
        Offer edited = service.editOffer(id, name2, description2, effect, start, end, null);

        // Assert
        assertNotNull(edited);
        assertEquals(name2, edited.getName());
        assertEquals(description2, edited.getDescription());
        assertNull(edited.getVideoGame());
        verify(repo, times(1)).save(offer);
    }

    @Test
    public void testEditOfferInvalidEffect() {
        //Arrange
        int id = 12; // Set a unique ID for the category

        // Mock behavior of repository
        when(repo.findOfferById(id)).thenReturn(offer);
        when(repo.save(any(Offer.class))).thenReturn(offer);

        // Act
        Offer edited = service.editOffer(id, name, description, "12.22", start, end, null);

        // Assert
        assertEquals("12.22", edited.getEffect());
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.editOffer(id, name, description, "$22", start, end, game));
        assertEquals("Discount format must either be \"XX%\" percent off or \"XX.YY\" flat decrease.", e.getMessage());
    }
}