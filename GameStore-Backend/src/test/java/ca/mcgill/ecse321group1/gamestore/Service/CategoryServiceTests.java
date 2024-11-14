package ca.mcgill.ecse321group1.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

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
    public void testReadCategoryByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findCategoryById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getCategory(id));
        assertEquals("There is no category with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteCategoryByValidID() {
        // Arrange
        int id = 12;
        when(repo.existsById(id)).thenReturn(true);
        //when(repo.findCategoryById(id)).thenReturn();

        // Act
        service.deleteCategory(id);

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
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteCategory(id));
        assertEquals(id + " cannot be deleted as it does not correspond to an extant Category!", e.getMessage());
    }

    @Test
    public void testCreateInvalidDuplicateCategory() {
        // Arrange
        String name = "ACTION";
        String description = "Fighting, movement, violence!";
        String description2 = "Action deserves poetry! Choreography! Passion!";
        Category action = new Category();
        action.setName(name);
        action.setDescription(description);

        // Act
        //service.createCategory(name, description);
        ArrayList<Category> list = new ArrayList<>();
        list.add(action);
        when(repo.findAll()).thenReturn(list);

        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.createCategory(name, description2));
        assertEquals("Category with name \"" + name + "\" already exists!", e.getMessage());
    }

    @Test
    public void testEditValidCategory() {
        //Arrange
        int id = 12; // Set a unique ID for the category
        String name = "ACTION";
        String description = "Fighting, movement, violence!";
        String name2 = "Action";
        String description2 = "Action deserves poetry! Choreography! Passion!";

        Category action = new Category();
        action.setId(id); // Ensure the ID is set
        action.setName(name);
        action.setDescription(description);

        // Mock behavior of repository
        when(repo.findById(id)).thenReturn(Optional.of(action));
        when(repo.save(any(Category.class))).thenReturn(action);

        // Act
        Category edited = service.editCategory(id, name2, description2);

        // Assert
        assertNotNull(edited);
        assertEquals(name2, edited.getName());
        assertEquals(description2, edited.getDescription());
        verify(repo, times(1)).save(action);
    }

    @Test
    public void testEditCategoryInvalidDescription() {
        //Arrange
        int id = 12; // Set a unique ID for the category
        String name = "ACTION";
        String description = "Fighting, movement, violence!";
        String description2 = "aa";

        Category action = new Category();
        action.setId(id); // Ensure the ID is set
        action.setName(name);
        action.setDescription(description);

        // Mock behavior of repository
        when(repo.findById(id)).thenReturn(Optional.of(action));
        when(repo.save(any(Category.class))).thenReturn(action);

        // Act

        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.editCategory(id, name, description2));
        assertEquals("Category descriptions must be at least 3 characters long!", e.getMessage());
    }
}