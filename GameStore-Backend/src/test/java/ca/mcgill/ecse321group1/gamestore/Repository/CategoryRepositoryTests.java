package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Category;

import java.sql.Date;
import java.util.*;

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
        // Create and Save Category
        String name = "Action";
        String description = "Focuses on physical challenges";
        Category category = new Category(name, description);
        category = categoryRepository.save(category);

        // Read Category from database
        int id = category.getId();
        Category categoryFromDb = categoryRepository.findCategoryById(id);

        // Assert Correct Responses
        assertNotNull(categoryFromDb);
        assertEquals(categoryFromDb.getName(), name);
        assertEquals(categoryFromDb.getDescription(), description);
    }
}