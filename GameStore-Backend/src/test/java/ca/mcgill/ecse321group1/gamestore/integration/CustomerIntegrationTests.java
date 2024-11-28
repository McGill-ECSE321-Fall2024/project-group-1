package ca.mcgill.ecse321group1.gamestore.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)


public class CustomerIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private VideoGameRepository videoGameRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    private static final String VALID_USERNAME = "Jonathan";
    private static final String VALID_EMAIL = "Jonathan@outlook.com";
    private static final String VALID_PASSWORD = "ILoveFortnite2003";
    private static final String VALID_ADDRESS = "123 Sesame Street, New York, New York, USA, 123456";
    private static final String VALID_PHONE_NUMBER = "604604604";

    private static final String NEW_USERNAME = "Terrance";
    private static final String NEW_EMAIL = "Terrance@outlook.com";
    private static final String NEW_PASSWORD = "IHateFortnite2003";
    private static final String NEW_ADDRESS = "100 Sesame Street, New York, New York, USA, 123456";
    private static final String NEW_PHONE_NUMBER = "778778778";


    private static final String CATEGORY_NAME = "Adventure";
    private static final String CATEGORY_DESC = "Story based RPG";

    private static final String GAME_NAME = "Zelda Raid Shadow Legends";
    private static final String GAME_DESCRIPTION = "A Zelda walks into a bar";
    private static final float GAME_PRICE = 19.99f;
    private static final int GAME_QUANTITY = 25;
    private static final LocalDate GAME_DATE = java.sql.Date.valueOf("2023-11-08").toLocalDate();

    private int customerId;
    private int categoryId;
    private int videoGameId;

    @AfterAll
    public void clearDatabase() {
        customerRepo.deleteAll();
        videoGameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidCustomer() {
        // Arrange
        PersonRequestDto request = new PersonRequestDto(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD, VALID_ADDRESS, VALID_PHONE_NUMBER);

        // Act
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/customer", request, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_USERNAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_ADDRESS, response.getBody().getAddress());
        assertEquals(VALID_PHONE_NUMBER, response.getBody().getPhoneNumber());
        assertTrue(response.getBody().getId() > 0, "The ID should be positive.");
        customerId = response.getBody().getId();
    }

    @Test
    @Order(2)
    public void testCreateInvalidCustomer() {
        // Arrange
        PersonRequestDto request = new PersonRequestDto(null, VALID_EMAIL, VALID_PASSWORD, VALID_ADDRESS, VALID_PHONE_NUMBER);

        // Act
        ResponseEntity<PersonResponseDto> response = client.postForEntity("/customer", request, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetValidCustomer() {
        // Arrange
        String url = String.format("/customer/%d", this.customerId);

        // Act
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.customerId, response.getBody().getId());
        assertEquals(VALID_USERNAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_ADDRESS, response.getBody().getAddress());
        assertEquals(VALID_PHONE_NUMBER, response.getBody().getPhoneNumber());
    }

    @Test
    @Order(4)
    public void testGetInvalidCustomer() {
        // Arrange
        String url = String.format("/customer/%d", this.customerId + 1);

        // Act
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert 
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testEditInvalidCustomer() {
        // Arrange
        String url = String.format("/customer/%d", this.customerId);
        PersonRequestDto request = new PersonRequestDto(null, VALID_EMAIL, VALID_PASSWORD, VALID_ADDRESS, VALID_PHONE_NUMBER);

        // Act
        client.put(url, request);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.customerId,response.getBody().getId());
        assertEquals(VALID_USERNAME, response.getBody().getUsername());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_ADDRESS, response.getBody().getAddress());
        assertEquals(VALID_PHONE_NUMBER, response.getBody().getPhoneNumber());
    }

    @Test
    @Order(6)
    public void testEditValidCustomer() {
        // Arrange
        String url = String.format("/customer/%d", this.customerId);
        PersonRequestDto request = new PersonRequestDto(NEW_USERNAME, NEW_EMAIL, NEW_PASSWORD, NEW_ADDRESS, NEW_PHONE_NUMBER);

        // Act 
        client.put(url, request);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

         // Assert
         assertNotNull(response);
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertEquals(this.customerId,response.getBody().getId());
         assertEquals(NEW_USERNAME, response.getBody().getUsername());
         assertEquals(NEW_EMAIL, response.getBody().getEmail());
         assertEquals(NEW_ADDRESS, response.getBody().getAddress());
         assertEquals(NEW_PHONE_NUMBER, response.getBody().getPhoneNumber());
    }

    @Test
    @Order(7)
    public void testAddGameToCustomerCart() {
        // First create category that can be used for video game
        CategoryRequestDto categoryRequest = new CategoryRequestDto(CATEGORY_NAME, CATEGORY_DESC);

        ResponseEntity<CategoryResponseDto> categoryResponse = client.postForEntity("/category", categoryRequest, CategoryResponseDto.class);
        assertNotNull(categoryResponse);
        assertEquals(CATEGORY_NAME, categoryResponse.getBody().getName());
        assertEquals(CATEGORY_DESC, categoryResponse.getBody().getDescription());
        categoryId = categoryResponse.getBody().getId();

        // Then create video game
        VideoGameRequestDto videoGameRequest = new VideoGameRequestDto(GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY,GAME_DATE, categoryId);

        ResponseEntity<VideoGameResponseDto> videoGameResponse = client.postForEntity("/videogame", videoGameRequest, VideoGameResponseDto.class);
        assertNotNull(videoGameResponse);
        assertEquals(GAME_NAME, videoGameResponse.getBody().getName());
        assertEquals(GAME_DESCRIPTION, videoGameResponse.getBody().getDescription());
        assertEquals(GAME_PRICE, videoGameResponse.getBody().getPrice());
        assertEquals(GAME_QUANTITY, videoGameResponse.getBody().getQuantity());
        assertEquals(GAME_DATE, videoGameResponse.getBody().getDate());
        assertEquals("Pending", videoGameResponse.getBody().getStatus().toString());
        videoGameId = videoGameResponse.getBody().getId();

        // Arrange (5 copies of game)
        String url = String.format("/customer/%d/game/%d/quantity/%d", this.customerId, this.videoGameId, 1);

        // Act
        ResponseEntity<PersonResponseDto> response = client.postForEntity(url, null, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.customerId,response.getBody().getId());
        assertEquals(NEW_USERNAME, response.getBody().getUsername());
        assertEquals(NEW_EMAIL, response.getBody().getEmail());
        assertEquals(NEW_ADDRESS, response.getBody().getAddress());
        assertEquals(NEW_PHONE_NUMBER, response.getBody().getPhoneNumber());
    }

}
