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

    private static final String GAME_2_NAME = "Fornite Save the World";
    private static final String GAME_2_DESCRIPTION = "Game still in beta";
    private static final float GAME_2_PRICE = 20.99f;
    private static final int GAME_2_QUANTITY = 25;
    private static final LocalDate GAME_2_DATE = java.sql.Date.valueOf("2002-11-08").toLocalDate();

    private int customerId;
    private int categoryId;
    private int videoGameId;
    private int videoGame1Id;
    private int videoGame2Id;

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

        // Arrange
        String url = String.format("/customer/%d/cart/%d/quantity/%d", this.customerId, this.videoGameId, 1);

        // Act
        client.put(url, null);

        // Assert
        // assertNotNull(response);
        // assertEquals(HttpStatus.OK, response.getStatusCode());
        // assertEquals(this.customerId,response.getBody().getId());
        // assertEquals(NEW_USERNAME, response.getBody().getUsername());
        // assertEquals(NEW_EMAIL, response.getBody().getEmail());
        // assertEquals(NEW_ADDRESS, response.getBody().getAddress());
        // assertEquals(NEW_PHONE_NUMBER, response.getBody().getPhoneNumber());
        // assertEquals(1, response.getBody().getCart().size());
        // assertEquals(videoGameId, response.getBody().getCart().get(0).getId());
    }

    @Test
    @Order(8)
    public void testAddGameToWishlist() {
        // Arrange
        String wishlistUrl = String.format("/customer/%d/wishlist/%d", this.customerId, this.videoGameId);
        String customerUrl = String.format("/customer/%d", this.customerId);

        // Act
        client.put(wishlistUrl, null);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(customerUrl, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.customerId,response.getBody().getId());
        assertEquals(NEW_USERNAME, response.getBody().getUsername());
        assertEquals(NEW_EMAIL, response.getBody().getEmail());
        assertEquals(NEW_ADDRESS, response.getBody().getAddress());
        assertEquals(NEW_PHONE_NUMBER, response.getBody().getPhoneNumber());
        assertEquals(1, response.getBody().getWishlist().size());
        assertEquals(videoGameId, response.getBody().getWishlist().get(0).getId());
    }

    @Test
    @Order(9)
    public void testRemoveGameFromWishlist() {
        // Arrange
        String deleteUrl = String.format("/customer/%d/cart/%d", this.customerId, this.videoGameId);
        String custUrl = String.format("/customer/%d", this.customerId);

        // Act
        client.delete(deleteUrl);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(custUrl, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.customerId,response.getBody().getId());
        assertEquals(NEW_USERNAME, response.getBody().getUsername());
        assertEquals(NEW_EMAIL, response.getBody().getEmail());
        assertEquals(NEW_ADDRESS, response.getBody().getAddress());
        assertEquals(NEW_PHONE_NUMBER, response.getBody().getPhoneNumber());
         assertEquals(0, response.getBody().getCart().size());
    }

    @Test
    @Order(10)
    public void testRemoveGameFromCart() {
        // Arrange
        String deleteUrl = String.format("/customer/%d/wishlist/%d", this.customerId, this.videoGameId);
        String custUrl = String.format("/customer/%d", this.customerId);

        // Act
        client.delete(deleteUrl);
        ResponseEntity<PersonResponseDto> response = client.getForEntity(custUrl, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.customerId,response.getBody().getId());
        assertEquals(NEW_USERNAME, response.getBody().getUsername());
        assertEquals(NEW_EMAIL, response.getBody().getEmail());
        assertEquals(NEW_ADDRESS, response.getBody().getAddress());
        assertEquals(NEW_PHONE_NUMBER, response.getBody().getPhoneNumber());
         assertEquals(0, response.getBody().getWishlist().size());
    }

    @Test
    @Order(11)
    public void testClearCart() {
        // First add two video games to cart
        VideoGameRequestDto videoGame1Request = new VideoGameRequestDto(GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY,GAME_DATE, this.categoryId);
        VideoGameRequestDto videoGame2Request = new VideoGameRequestDto(GAME_2_NAME, GAME_2_DESCRIPTION, GAME_2_PRICE, GAME_2_QUANTITY,GAME_2_DATE, this.categoryId);

        ResponseEntity<VideoGameResponseDto> videoGame1Response = client.postForEntity("/videogame", videoGame1Request, VideoGameResponseDto.class);
        assertNotNull(videoGame1Response);
        assertEquals(GAME_NAME, videoGame1Response.getBody().getName());
        assertEquals(GAME_DESCRIPTION, videoGame1Response.getBody().getDescription());
        assertEquals(GAME_PRICE, videoGame1Response.getBody().getPrice());
        assertEquals(GAME_QUANTITY, videoGame1Response.getBody().getQuantity());
        assertEquals(GAME_DATE, videoGame1Response.getBody().getDate());
        assertEquals("Pending", videoGame1Response.getBody().getStatus().toString());
        videoGame1Id = videoGame1Response.getBody().getId();


        ResponseEntity<VideoGameResponseDto> videoGame2Response = client.postForEntity("/videogame", videoGame2Request, VideoGameResponseDto.class);
        assertNotNull(videoGame2Response);
        assertEquals(GAME_2_NAME, videoGame2Response.getBody().getName());
        assertEquals(GAME_2_DESCRIPTION, videoGame2Response.getBody().getDescription());
        assertEquals(GAME_2_PRICE, videoGame2Response.getBody().getPrice());
        assertEquals(GAME_2_QUANTITY, videoGame2Response.getBody().getQuantity());
        assertEquals(GAME_2_DATE, videoGame2Response.getBody().getDate());
        assertEquals("Pending", videoGame2Response.getBody().getStatus().toString());
        videoGame2Id = videoGame2Response.getBody().getId();

        // Add created video games to cart
        String addGame1Url = String.format("/customer/%d/cart/%d/quantity/%d", this.customerId, this.videoGame1Id, 1);
        String addGame2Url = String.format("/customer/%d/cart/%d/quantity/%d", this.customerId, this.videoGame2Id, 1);

        client.put(addGame1Url, null);
        client.put(addGame2Url, null);

        // assertNotNull(game2CartResponse);
        // assertEquals(HttpStatus.OK, game2CartResponse.getStatusCode());
        // assertEquals(this.customerId,game2CartResponse.getBody().getId());
        // assertEquals(NEW_USERNAME, game2CartResponse.getBody().getUsername());
        // assertEquals(NEW_EMAIL, game2CartResponse.getBody().getEmail());
        // assertEquals(NEW_ADDRESS, game2CartResponse.getBody().getAddress());
        // assertEquals(NEW_PHONE_NUMBER, game2CartResponse.getBody().getPhoneNumber());
        // assertEquals(2, game2CartResponse.getBody().getCart().size());
        // assertEquals(videoGame1Id, game2CartResponse.getBody().getCart().get(0).getId());
        // assertEquals(videoGame2Id, game2CartResponse.getBody().getCart().get(1).getId());

        // Arrange
        String deleteUrl = String.format("/customer/%d/cart", this.customerId);
        String custUrl = String.format("/customer/%d", this.customerId);

        // Act
        client.delete(deleteUrl);
        ResponseEntity<PersonResponseDto> cartDeleteResponse = client.getForEntity(custUrl, PersonResponseDto.class);

        // Arrange
        assertNotNull(cartDeleteResponse);
        assertEquals(HttpStatus.OK, cartDeleteResponse.getStatusCode());
        assertEquals(this.customerId,cartDeleteResponse.getBody().getId());
        assertEquals(NEW_USERNAME, cartDeleteResponse.getBody().getUsername());
        assertEquals(NEW_EMAIL, cartDeleteResponse.getBody().getEmail());
        assertEquals(NEW_ADDRESS, cartDeleteResponse.getBody().getAddress());
        assertEquals(NEW_PHONE_NUMBER, cartDeleteResponse.getBody().getPhoneNumber());
        assertEquals(0, cartDeleteResponse.getBody().getCart().size());
    }

}
