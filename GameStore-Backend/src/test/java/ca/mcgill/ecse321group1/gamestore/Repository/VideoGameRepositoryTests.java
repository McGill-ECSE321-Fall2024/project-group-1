package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame.Status;

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
        // Create category
        String categoryName = "Adventure";
        String categoryDescription = "Adventure games with thrilling experiences.";
        Category category = new Category(categoryName, categoryDescription);
        category = categoryRepository.save(category);

        // Create video game
        String gameName = "Lost Treasures";
        String description = "A thrilling adventure in search of hidden treasures.";
        String price = "49.99";
        String quantity = "10";
        String date = "2024-10-12";
        VideoGame.Status status = VideoGame.Status.Active;
        VideoGame videoGame = new VideoGame(gameName, description, price, quantity, date, status, category);

        // Save video game
        videoGame = videoGameRepository.save(videoGame);
        String savedName = videoGame.getName();

        // Read video game from database
        VideoGame videoGameFromDb = videoGameRepository.findVideoGameById(videoGame.getId());

        // Assert correct response
        assertNotNull(videoGameFromDb);
        assertEquals(videoGameFromDb.getName(), gameName);
        assertEquals(videoGameFromDb.getDescription(), description);
        assertEquals(videoGameFromDb.getPrice(), price);
        assertEquals(videoGameFromDb.getQuantity(), quantity);
        assertEquals(videoGameFromDb.getDate(), date);
        assertEquals(videoGameFromDb.getStatus(), status);
        assertEquals(videoGameFromDb.getCategory().getName(), categoryName);
    }
}