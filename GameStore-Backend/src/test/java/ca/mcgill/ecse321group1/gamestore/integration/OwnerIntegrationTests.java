package ca.mcgill.ecse321group1.gamestore.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.repository.OwnerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class OwnerIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private OwnerRepository ownerRepo;

    private static final String VALID_USERNAME = "Jonathan";
    private static final String NEW_USERNAME = "Alexander";

    private static final String VALID_EMAIL = "Jonathan@outlook.com";
    private static final String NEW_EMAIL = "Alexander@outlook.com";

    private static final String VALID_PASSWORD = "ILoveApexLegends2003";
    private static final String NEW_PASSWORD = "ILoveFortnite2003";
    private static final String INVALID_PASSWORD = "123pass";

    private static final String SECOND_USERNAME = "Terrance";
    private static final String SECOND_EMAIL = "Terrance@outlook.com";
    private static final String SECOND_PASSWORD = "ILovePubg2001";

    private int ownerId;
    private int newOwnerId;

    @AfterAll
    public void clearDatabase() {
        ownerRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidOwner() {
        // Arrange
        PersonRequestDto request = new PersonRequestDto(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD);

        // Act
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/owner", request, PersonResponseDto.class);


        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_USERNAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertTrue(response.getBody().getId() > 0, "The ID should be positive.");
        ownerId = response.getBody().getId();
    }

    @Test
    @Order(2)
    public void testCreateInvalidOwner() {
        // Arrange
        PersonRequestDto request = new PersonRequestDto(VALID_USERNAME, VALID_EMAIL, INVALID_PASSWORD);

        // Act
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/owner", request, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetValidOwner() {
        // Arrange
        String url = String.format("/owner/%d", this.ownerId);

        // Act
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.ownerId, response.getBody().getId());
        assertEquals(VALID_USERNAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        }

    @Test
    @Order(4)
    public void testGetInvalidOwner() {
        // Arrange 
        String url = String.format("/owner/%d", this.ownerId + 1);

        // Act
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody().getUsername());
        assertEquals(null, response.getBody().getEmail());
    }

    @Test
    @Order(5)
    public void testEditInvalidOwner() {
        // Arrange
        String url = String.format("/owner/%d", this.ownerId);
        PersonRequestDto request = new PersonRequestDto(null, VALID_EMAIL, VALID_PASSWORD);

        // Act
        client.put(url, request);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.ownerId, response.getBody().getId());
        assertEquals(VALID_USERNAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());

    }

    @Test
    @Order(6)
    public void testEditValidOwner() {
        // Arrange
        String url = String.format("/owner/%d", this.ownerId);
        PersonRequestDto request = new PersonRequestDto(NEW_USERNAME, NEW_EMAIL, NEW_PASSWORD);
        
        // Act
        client.put(url, request);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.ownerId, response.getBody().getId());
        assertEquals(NEW_USERNAME, response.getBody().getUsername());
        assertEquals(NEW_EMAIL, response.getBody().getEmail());
    }

    @Test
    @Order(7)
    public void testDeleteInvalidOwner() {
        // Arrange
        String invalidUrl = String.format("/owner/%d", this.ownerId + 1);
        String validUrl = String.format("/owner/%d", this.ownerId);

        
        // Act
        client.delete(invalidUrl);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(validUrl, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.ownerId, response.getBody().getId());
        assertEquals(NEW_USERNAME, response.getBody().getUsername());
        assertEquals(NEW_EMAIL, response.getBody().getEmail());
    }

    @Test
    @Order(8)
    public void testDeleteLastOwner() {
        // Arrange
        String url = String.format("/owner/%d", this.ownerId);

        // Act
        client.delete(url);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NEW_USERNAME, response.getBody().getUsername());
        assertEquals(NEW_EMAIL, response.getBody().getEmail());
    }

    @Test
    @Order(9)
    public void testCreateNewOwnerAndDeleteOldOwner() {
        // Arrange
        PersonRequestDto newOwner = new PersonRequestDto(SECOND_USERNAME, SECOND_EMAIL, SECOND_PASSWORD);
        String oldOwnerUrl = String.format("/owner/%d", this.ownerId); // URL to delete old owner



        // Act
        ResponseEntity<PersonResponseDto> newOwnerResponse = client.postForEntity("/owner", newOwner, PersonResponseDto.class);
        client.delete(oldOwnerUrl); // Delete old owner
        newOwnerId = newOwnerResponse.getBody().getId(); // Save ID of new owner
        String newOwnerUrl = String.format("/owner/%d", this.newOwnerId); // Update url to new owner ID
        ResponseEntity<PersonResponseDto> verifyNewOwnerResponse = client.getForEntity(newOwnerUrl, PersonResponseDto.class); // Get new owner to verify change successful
        ResponseEntity<PersonResponseDto> verifyOldOwnerResponse = client.getForEntity(oldOwnerUrl, PersonResponseDto.class);

        // Assert
        assertNotNull(verifyOldOwnerResponse);
        assertEquals(HttpStatus.BAD_REQUEST, verifyOldOwnerResponse.getStatusCode()); // Verify that old owner is deleted

        assertNotNull(verifyNewOwnerResponse); 
        assertEquals(HttpStatus.OK, verifyNewOwnerResponse.getStatusCode());
        assertEquals(SECOND_USERNAME, verifyNewOwnerResponse.getBody().getUsername());
        assertEquals(SECOND_EMAIL, verifyNewOwnerResponse.getBody().getEmail());
    }
}

