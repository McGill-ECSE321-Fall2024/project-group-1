package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Category;

@SpringBootTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    public void clearDatabase() {
        categoryRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCategory() {
        // Create category
        String name = "Action";
        String description = "Action games category";
        Category actionCategory = new Category();
        actionCategory.setName(name);
        actionCategory.setDescription(description);

        // Save category
        actionCategory = categoryRepository.save(actionCategory);
        String savedName = actionCategory.getName();

        // Read category from database
        Category actionCategoryFromDb = categoryRepository.findCategoryByName(savedName);

        // Assert correct response
        assertNotNull(actionCategoryFromDb);
        assertEquals(actionCategoryFromDb.getName(), name);
        assertEquals(actionCategoryFromDb.getDescription(), description);
    }
}