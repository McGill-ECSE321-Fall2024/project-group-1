package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame.Status;

import java.sql.Date;
import java.util.*;

@SpringBootTest
public class VideoGameRepositoryTests {

    @Autowired
    private VideoGameRepository videoGameRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    public void clearDatabase() {
        videoGameRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadVideoGame() {
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

        // Read owner from database
        int id = videoGame.getId();
        VideoGame videoGameFromDb = videoGameRepository.findVideoGameById(id);

        // Assert correct response
        assertNotNull(videoGameFromDb);
        assertEquals(videoGameFromDb.getName(), videoGameName);
        assertEquals(videoGameFromDb.getDescription(), videoGameDescription);
        assertEquals(videoGameFromDb.getPrice(), price);
        assertEquals(videoGameFromDb.getQuantity(), quantity);
        assertEquals(videoGameFromDb.getDate().toLocalDate(), videoGameDate.toLocalDate());
        assertEquals(videoGameFromDb.getStatus(), status);
        assertEquals(videoGameFromDb.getCategory().getId(), category.getId());
    }
}