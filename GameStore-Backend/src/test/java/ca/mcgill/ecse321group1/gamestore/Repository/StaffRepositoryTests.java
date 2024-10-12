package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Staff;

@SpringBootTest
public class StaffRepositoryTests {

    @Autowired
    private StaffRepository staffRepository;

    @AfterEach
    public void clearDatabase() {
        staffRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadStaff() {
        // Create staff member
        String username = "StaffUser";
        String email = "staffuser@example.com";
        String passwordHash = "staffpassword";
        Staff staff = new Staff(username, email, passwordHash);

        // Save staff member
        staff = staffRepository.save(staff);
        String savedUsername = staff.getUsername();

        // Read staff member from database
        Staff staffFromDb = staffRepository.findStaffByUsername(savedUsername);

        // Assert correct response
        assertNotNull(staffFromDb);
        assertEquals(staffFromDb.getUsername(), username);
        assertEquals(staffFromDb.getEmail(), email);
        assertEquals(staffFromDb.getPasswordHash(), passwordHash);
    }
}