package ca.mcgill.ecse321group1.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;

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

        // Read video game from database
        int id = videoGame.getId();
        VideoGame videoGameFromDb = videoGameRepository.findVideoGameById(id);

        // Assert correct response
        assertNotNull(videoGameFromDb);
        assertEquals(videoGameName, videoGameFromDb.getName());
        assertEquals(videoGameDescription, videoGameFromDb.getDescription());
        assertEquals(price, videoGameFromDb.getPrice());
        assertEquals(quantity, videoGameFromDb.getQuantity());
        assertEquals(videoGameDate.toLocalDate(), videoGameFromDb.getDate().toLocalDate());
        assertEquals(status, videoGameFromDb.getStatus());
        assertEquals(category.getId(), videoGameFromDb.getCategory().getId());
    }
}
