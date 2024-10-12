package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Owner;

@SpringBootTest
public class OwnerRepositoryTests {

    @Autowired
    private OwnerRepository ownerRepository;

    @AfterEach
    public void clearDatabase() {
        ownerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOwner() {
        // Create owner
        String username = "AdminOwner";
        String email = "adminowner@example.com";
        String passwordHash = "adminpassword";
        Owner owner = new Owner(username, email, passwordHash);

        // Save owner
        owner = ownerRepository.save(owner);
        String savedUsername = owner.getUsername();

        // Read owner from database
        Owner ownerFromDb = ownerRepository.findOwnerByUsername(savedUsername);

        // Assert correct response
        assertNotNull(ownerFromDb);
        assertEquals(ownerFromDb.getUsername(), username);
        assertEquals(ownerFromDb.getEmail(), email);
        assertEquals(ownerFromDb.getPasswordHash(), passwordHash);
    }
}