package ca.mcgill.ecse321group1.gamestore.Service;

import ca.mcgill.ecse321group1.gamestore.model.Staff;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.StaffRepository;
import ca.mcgill.ecse321group1.gamestore.service.StaffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StaffServiceTests {
    @Mock
    private CustomerRepository cust_repo;
    @Mock
    private StaffRepository repo;
    @Mock
    private OwnerRepository owner_repo;

    @InjectMocks
    private StaffService service;


    private Staff BOB;
    private final int ID = 31;
    private final String[] fields = new String[]{"BobWilson", "Bobby@gmail.com", "Password123"};

    @BeforeEach
    public void preparationStaff() {
        BOB = new Staff(ID, fields[0], fields[1], PersonServiceTestHelper.hash_password(fields[2]));
        when(owner_repo.findAll()).thenReturn(new ArrayList<>()); // no existing owners
        when(cust_repo.findAll()).thenReturn(new ArrayList<>()); // no existing customers
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateValidStaff() {
        // Arrange
        when(repo.save(any(Staff.class))).thenReturn(BOB);
        when(repo.findAll()).thenReturn(new ArrayList<>()); // Simulating that BOB already exists

        // Act
        Staff createdStaff = service.createStaff(fields[0], fields[1], fields[2]);

        // Assert
        assertNotNull(createdStaff);
        assertEquals(fields[0], createdStaff.getUsername());
        assertEquals(fields[1], createdStaff.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), createdStaff.getPasswordHash());
        verify(repo, times(1)).save(BOB);//saved once, on creation
    }
    @SuppressWarnings("null")
    @Test
    public void testCreateInvalidStaff() {
        // Arrange
        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                service.createStaff("abc", fields[1], fields[2]));
        assertEquals("Usernames must be at least 6 characters long!", e.getMessage());
    }

    @Test
    public void testReadStaffByValidId() {
        // Arrange
        when(repo.findStaffById(ID)).thenReturn(BOB);

        // Act
        Staff retrieved = service.getStaff(ID);

        // Assert
        assertNotNull(retrieved);
        assertEquals(fields[0], retrieved.getUsername());
        assertEquals(fields[1], retrieved.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), retrieved.getPasswordHash());
    }

    @Test
    public void testReadStaffByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findStaffById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getStaff(id));
        assertEquals("There is no staff with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteStaffByValidID() {
        // Arrange
        int id = 12;
        when(repo.existsById(id)).thenReturn(true);
        when(repo.count()).thenReturn(2L);//promise it at least two exist
        //when(repo.findCategoryById(id)).thenReturn();

        // Act
        service.deleteStaff(id);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteStaffByInvalidID() {
        // Arrange
        int id = 42;
        when(repo.existsById(id)).thenReturn(false);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteStaff(id));
        assertEquals(id + " cannot be deleted as it does not correspond to an extant Staff!", e.getMessage());
    }

    @Test
    public void testCreateInvalidDuplicateStaff() {
        // Arrange
        when(repo.findAll()).thenReturn(new ArrayList<>(Collections.singleton(BOB))); // Simulating that BOB already exists

        // Act

        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.createStaff(fields[0], fields[1], fields[2]));
        assertEquals("Staff with username \"" + fields[0] + "\" already exists!", e.getMessage());
    }

    @Test
    public void testEditValidStaff() {
        //Arrange
        String new_username = "Jane Doe";
        String new_email = "PullitzerPrize@WashingtonPst.com"; //not a scam


        // Mock behavior of repository
        when(repo.findById(ID)).thenReturn(Optional.of(BOB));
        when(repo.save(any(Staff.class))).thenReturn(BOB);

        // Act
        Staff edited = service.editStaff(ID, new_username, new_email, fields[2]);

        // Assert
        assertNotNull(edited);
        assertEquals(new_username, edited.getUsername());
        assertEquals(new_email, edited.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), edited.getPasswordHash());
        verify(repo, times(1)).save(BOB);
    }
}