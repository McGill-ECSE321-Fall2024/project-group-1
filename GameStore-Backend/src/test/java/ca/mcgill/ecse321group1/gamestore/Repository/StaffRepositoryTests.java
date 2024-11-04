package ca.mcgill.ecse321group1.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Staff;
import ca.mcgill.ecse321group1.gamestore.repository.StaffRepository;

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
        // Create and Save Staff
        String username = "MrStaff";
        String email = "mrstaff@example.com";
        String passwordHash = "password789";

        Staff staff = new Staff();
        staff.setUsername(username);
        staff.setEmail(email);
        staff.setPasswordHash(passwordHash);
        staff = staffRepository.save(staff);

        // Read staff from database
        int id = staff.getId();
        Staff staffFromDb = staffRepository.findStaffById(id);

        // Assert correct response
        assertNotNull(staffFromDb);
        assertEquals(username, staffFromDb.getUsername());
        assertEquals(email, staffFromDb.getEmail());
        assertEquals(passwordHash, staffFromDb.getPasswordHash());
    }
}
