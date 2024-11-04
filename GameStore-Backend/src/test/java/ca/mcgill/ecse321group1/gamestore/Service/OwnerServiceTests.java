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
import ca.mcgill.ecse321group1.gamestore.service.OwnerService;
import ca.mcgill.ecse321group1.gamestore.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OwnerServiceTests {
    @Mock
    private CustomerRepository cust_repo;
    @Mock
    private StaffRepository staff_repo;
    @Mock
    private OwnerRepository repo;

    @InjectMocks
    private OwnerService service;


    private Owner BOB;
    private final int ID = 31;
    private final String[] fields = new String[]{"BobWilson", "Bobby@gmail.com", "Password123"};

    @BeforeEach
    public void preparationOwner() {
        BOB = new Owner(ID, fields[0], fields[1], PersonServiceTestHelper.hash_password(fields[2]));
        when(staff_repo.findAll()).thenReturn(new ArrayList<>()); // no existing staff
        when(cust_repo.findAll()).thenReturn(new ArrayList<>()); // no existing customers
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateValidOwner() {
        // Arrange
        when(repo.save(any(Owner.class))).thenReturn(BOB);
        when(repo.findAll()).thenReturn(new ArrayList<>()); // Simulating that BOB already exists

        // Act
        Owner createdOwner = service.createOwner(fields[0], fields[1], fields[2]);

        // Assert
        assertNotNull(createdOwner);
        assertEquals(fields[0], createdOwner.getUsername());
        assertEquals(fields[1], createdOwner.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), createdOwner.getPasswordHash());
        verify(repo, times(1)).save(BOB);//saved once, on creation
    }
    @SuppressWarnings("null")
    @Test
    public void testCreateInvalidOwner() {
        // Arrange
        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                service.createOwner(fields[0], "notanemail", fields[2]));
        assertEquals("Emails must contain an \"@\" sign!", e.getMessage());
    }

    @Test
    public void testReadOwnerByValidId() {
        // Arrange
        when(repo.findOwnerById(ID)).thenReturn(BOB);

        // Act
        Owner retrieved = service.getOwner(ID);

        // Assert
        assertNotNull(retrieved);
        assertEquals(fields[0], retrieved.getUsername());
        assertEquals(fields[1], retrieved.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), retrieved.getPasswordHash());
    }

    @Test
    public void testReadOwnerByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findOwnerById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getOwner(id));
        assertEquals("There is no owner with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteOwnerByValidID() {
        // Arrange
        int id = 12;
        when(repo.existsById(id)).thenReturn(true);
        when(repo.count()).thenReturn(2L);//promise it at least two exist
        //when(repo.findCategoryById(id)).thenReturn();

        // Act
        service.deleteOwner(id);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteOwnerByInvalidID() {
        // Arrange
        int id = 42;
        when(repo.existsById(id)).thenReturn(false);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteOwner(id));
        assertEquals(id + " cannot be deleted as it does not correspond to an extant Owner!", e.getMessage());
    }

    @Test
    public void testDeleteLastOwner() {
        // Arrange
        int id = 42;
        when(repo.existsById(id)).thenReturn(true);
        when(repo.count()).thenReturn(1L);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteOwner(id));
        assertEquals("You cannot remove the last Owner.", e.getMessage());
    }

    @Test
    public void testCreateInvalidDuplicateOwner() {
        // Arrange
        when(repo.findAll()).thenReturn(new ArrayList<>(Collections.singleton(BOB))); // Simulating that BOB already exists

        // Act

        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.createOwner(fields[0], fields[1], fields[2]));
        assertEquals("Owner with username \"" + fields[0] + "\" already exists!", e.getMessage());
    }

    @Test
    public void testEditValidOwner() {
        //Arrange
        String new_username = "Jane Doe";
        String new_email = "PullitzerPrize@WashingtonPst.com"; //not a scam


        // Mock behavior of repository
        when(repo.findById(ID)).thenReturn(Optional.of(BOB));
        when(repo.save(any(Owner.class))).thenReturn(BOB);

        // Act
        Owner edited = service.editOwner(ID, new_username, new_email, fields[2]);

        // Assert
        assertNotNull(edited);
        assertEquals(new_username, edited.getUsername());
        assertEquals(new_email, edited.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), edited.getPasswordHash());
        verify(repo, times(1)).save(BOB);
    }
}