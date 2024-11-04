package ca.mcgill.ecse321group1.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.service.CategoryService;
import ca.mcgill.ecse321group1.gamestore.model.Category;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTests {
    @Mock
    private CategoryRepository repo;
    @InjectMocks
    private CategoryService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidCategory() {
        // Arrange
        String name = "ACTION";
        String description = "Fighting, movement, violence!";
        Category action = new Category();
        action.setName(name);
        action.setDescription(description);
        when(repo.save(any(Category.class))).thenReturn(action);
        // You could also do it with thenAnswer(), which is more flexible but more
        // verbose
        // when(repo.save(notNull(Person.class))).thenAnswer((InvocationOnMock iom) ->
        // iom.getArgument(0));

        // Act
        Category createdCategory = service.createCategory(name, description);

        // Assert
        assertNotNull(createdCategory);
        assertEquals(name, createdCategory.getName());
        assertEquals(description, createdCategory.getDescription());
        verify(repo, times(1)).save(action);//saved once, on creation
    }

    @Test
    public void testReadCategoryByValidId() {
        // Arrange
        int id = 42;
        Category action = new Category();
        action.setName("ACTION");
        action.setDescription("Ka-Pow! BOOM! SMASH!");
        when(repo.findCategoryById(id)).thenReturn(action);

        // Act
        Category retrieved = service.getCategory(id);

        // Assert
        assertNotNull(retrieved);
        assertEquals(action.getName(), retrieved.getName());
        assertEquals(action.getDescription(), retrieved.getDescription());
    }

    @Test
    public void testReadPersonByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findCategoryById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getCategory(id));
        assertEquals("There is no category with ID " + id + ".", e.getMessage());
        // assertThrows is basically like the following:
        // try {
        // service.findPersonById(id);
        // fail("No exception was thrown.");
        // } catch (IllegalArgumentException e) {
        // assertEquals("There is no person with ID " + id + ".", e.getMessage());
        // }
    }
}