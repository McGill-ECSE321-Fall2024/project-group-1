package ca.mcgill.ecse321group1.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Owner;
import ca.mcgill.ecse321group1.gamestore.repository.OwnerRepository;

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
        // Create and Save owner
        String username = "MrOwner";
        String email = "mrowner@example.com";
        String passwordHash = "password456";

        Owner owner = new Owner();
        owner.setUsername(username);
        owner.setEmail(email);
        owner.setPasswordHash(passwordHash);
        owner = ownerRepository.save(owner);

        // Read owner from database
        int id = owner.getId();
        Owner ownerFromDb = ownerRepository.findOwnerById(id);

        // Assert correct response
        assertNotNull(ownerFromDb);
        assertEquals(username, ownerFromDb.getUsername());
        assertEquals(email, ownerFromDb.getEmail());
        assertEquals(passwordHash, ownerFromDb.getPasswordHash());
    }
}
