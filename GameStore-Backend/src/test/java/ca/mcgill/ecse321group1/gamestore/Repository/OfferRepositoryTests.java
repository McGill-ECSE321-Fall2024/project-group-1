package ca.mcgill.ecse321group1.gamestore.Repository;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.Offer;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OfferRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OfferRepositoryTests {
    @Autowired
    private OfferRepository offerRepo;
    @Autowired
    private VideoGameRepository gameRepo;
    @Autowired
    private CategoryRepository catRepo;

    @AfterEach
    public void clearDatabase() {
        offerRepo.deleteAll();
        gameRepo.deleteAll();
        catRepo.deleteAll();
    }

    @Test
    public void testPersistAndLoadCategory() {
        String name = "Action";
        String description = "Focuses on physical challenges";
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category = catRepo.save(category);

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
        videoGame = gameRepo.save(videoGame);

        String oname = "Action";
        String odescription = "Focuses on physical challenges";
        String effect = "20%";
        Date start = new Date(0);
        Date end = new Date(123274821);

        Offer offer = new Offer();
        offer.setName(oname);
        offer.setDescription(odescription);
        offer.setEffect(effect);
        offer.setStartDate(start);
        offer.setEndDate(end);
        offer.setVideoGame(videoGame);

        offer = offerRepo.save(offer);

        // Read Category from database
        int id = offer.getId();
        Offer offerFromRepo = offerRepo.findOfferById(id);

        // Assert Correct Responses
        assertNotNull(offerFromRepo);
        assertEquals(oname, offerFromRepo.getName());
        assertEquals(odescription, offerFromRepo.getDescription());
        assertEquals(effect, offerFromRepo.getEffect());
        assertEquals(start.toString(), offerFromRepo.getStartDate().toString());
        assertEquals(end.toString(), offerFromRepo.getEndDate().toString());
        assertEquals(videoGame, offerFromRepo.getVideoGame());
    }
}
