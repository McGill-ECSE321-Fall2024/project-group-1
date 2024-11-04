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
import java.util.*;

import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.StaffRepository;
import ca.mcgill.ecse321group1.gamestore.service.CustomerService;
import ca.mcgill.ecse321group1.gamestore.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceTests {
    @Mock
    private CustomerRepository repo;
    @Mock
    private StaffRepository staff_repo;
    @Mock
    private OwnerRepository owner_repo;

    @InjectMocks
    private CustomerService service;


    private Customer BOB;
    private final int ID = 31;
    private final String[] fields = new String[]{"BobWilson", "Bobby@gmail.com", "Password123", "123 Mason Road, USA", "+2 248 893 524"};

    @BeforeEach
    public void preparationCustomer() {
        BOB = new Customer(ID, fields[0], fields[1], PersonServiceTestHelper.hash_password(fields[2]), fields[3], fields[4]);
        when(staff_repo.findAll()).thenReturn(new ArrayList<>()); // no existing staff
        when(owner_repo.findAll()).thenReturn(new ArrayList<>()); // no existing owner
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateValidCustomer() {
        // Arrange
        when(repo.save(any(Customer.class))).thenReturn(BOB);
        when(repo.findAll()).thenReturn(new ArrayList<>()); // Simulating that BOB already exists

        // Act
        Customer createdCustomer = service.createCustomer(fields[0], fields[1], fields[2], fields[3], fields[4]);

        // Assert
        assertNotNull(createdCustomer);
        assertEquals(fields[0], createdCustomer.getUsername());
        assertEquals(fields[1], createdCustomer.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), createdCustomer.getPasswordHash());
        assertEquals(fields[3], createdCustomer.getAddress());
        assertEquals(fields[4], createdCustomer.getPhoneNumber());
        verify(repo, times(1)).save(BOB);//saved once, on creation
    }
    @SuppressWarnings("null")
    @Test
    public void testCreateInvalidCustomer() {
        // Arrange
        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                service.createCustomer(fields[0], fields[1], "STRONG_PASSWORD", fields[3], fields[4]));
        assertEquals("Passwords must be at least 8 characters long, contain an uppercase and lowercase letter, and at least one number.", e.getMessage());
    }

    @Test
    public void testReadCustomerByValidId() {
        // Arrange
        when(repo.findCustomerById(ID)).thenReturn(BOB);

        // Act
        Customer retrieved = service.getCustomer(ID);

        // Assert
        assertNotNull(retrieved);
        assertEquals(fields[0], retrieved.getUsername());
        assertEquals(fields[1], retrieved.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), retrieved.getPasswordHash());
        assertEquals(fields[3], retrieved.getAddress());
        assertEquals(fields[4], retrieved.getPhoneNumber());
    }

    @Test
    public void testReadCustomerByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findCustomerById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getCustomer(id));
        assertEquals("There is no customer with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteCustomerByValidID() {
        // Arrange
        int id = 12;
        when(repo.existsById(id)).thenReturn(true);
        //when(repo.findCategoryById(id)).thenReturn();

        // Act
        service.deleteCategory(id);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }
*/
    @Test
    public void testDeleteCustomerByInvalidID() {
        // Arrange
        int id = 42;
        when(repo.existsById(id)).thenReturn(false);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteCategory(id));
        assertEquals(id + " cannot be deleted as it does not correspond to an extant Category!", e.getMessage());
    }

    @Test
    public void testCreateInvalidDuplicateCustomer() {
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
    public void testEditValidCustomer() {
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
    public void testEditCustomerInvalidPassword() {
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
}*/
}