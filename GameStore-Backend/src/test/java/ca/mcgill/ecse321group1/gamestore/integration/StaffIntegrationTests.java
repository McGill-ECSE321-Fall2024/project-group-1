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
import ca.mcgill.ecse321group1.gamestore.repository.StaffRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class StaffIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private StaffRepository staffRepo;

    private static final String VALID_USERNAME = "Jonathan";
    private static final String NEW_USERNAME = "Alexander";
    private static final String VALID_EMAIL = "Jonathan@outlook.com";
    private static final String NEW_EMAIL = "Alexander@outlook.com";
    private static final String VALID_PASSWORD = "ILoveApexLegends2003";
    private static final String NEW_PASSWORD = "ILoveFortnite2003";
    private static final String INVALID_PASSWORD = "123pass";

    private int staffId;

    @AfterAll
    public void clearDatabase() {
        staffRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidStaff() {
        // Arrange
        PersonRequestDto request = new PersonRequestDto(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD);

        // Act
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/staff", request, PersonResponseDto.class);


        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_USERNAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertTrue(response.getBody().getId() > 0, "The ID should be positive.");
        staffId = response.getBody().getId();
    }

    @Test
    @Order(2)
    public void testCreateInvalidStaff() {
        // Arrange
        PersonRequestDto request = new PersonRequestDto(VALID_USERNAME, VALID_EMAIL, INVALID_PASSWORD);

        // Act
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/staff", request, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetValidStaff() {
        // Arrange
        String url = String.format("/staff/%d", this.staffId);

        // Act
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.staffId, response.getBody().getId());
        assertEquals(VALID_USERNAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        }

    @Test
    @Order(4)
    public void testGetInvalidStaff() {
        // Arrange 
        String url = String.format("/staff/%d", this.staffId + 1);

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
    public void testEditInvalidStaff() {
        // Arrange
        String url = String.format("/staff/%d", this.staffId);
        PersonRequestDto request = new PersonRequestDto(null, VALID_EMAIL, VALID_PASSWORD);

        // Act
        client.put(url, request);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.staffId, response.getBody().getId());
        assertEquals(VALID_USERNAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());

    }

    @Test
    @Order(6)
    public void testEditValidStaff() {
        // Arrange
        String url = String.format("/staff/%d", this.staffId);
        PersonRequestDto request = new PersonRequestDto(NEW_USERNAME, NEW_EMAIL, NEW_PASSWORD);
        
        // Act
        client.put(url, request);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.staffId, response.getBody().getId());
        assertEquals(NEW_USERNAME, response.getBody().getUsername());
        assertEquals(NEW_EMAIL, response.getBody().getEmail());
    }

    @Test
    @Order(7)
    public void testDeleteInvalidStaff() {
        // Arrange
        String invalidUrl = String.format("/staff/%d", this.staffId + 1);
        String validUrl = String.format("/staff/%d", this.staffId);

        
        // Act
        client.delete(invalidUrl);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(validUrl, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.staffId, response.getBody().getId());
        assertEquals(NEW_USERNAME, response.getBody().getUsername());
        assertEquals(NEW_EMAIL, response.getBody().getEmail());
    }

    @Test
    @Order(8)
    public void testDeleteValidStaff() {
        // Arrange
        String url = String.format("/staff/%d", this.staffId);

        // Act
        client.delete(url);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
