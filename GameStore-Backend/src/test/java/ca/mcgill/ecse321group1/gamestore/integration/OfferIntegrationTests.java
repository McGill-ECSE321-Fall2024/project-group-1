package ca.mcgill.ecse321group1.gamestore.integration;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OfferRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.OfferRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.OfferResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;


import org.junit.jupiter.api.TestInstance.Lifecycle;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class OfferIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private OfferRepository offerRepo;
    @Autowired
    private VideoGameRepository videoGameRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    private static final String VALID_NAME = "MAY DAY MAYHEM";
    private static final String VALID_DESCRIPTION = "May Day Invasion X HUGE DISCOUNTS";
    private static final String VALID_EFFECT = "50%";
    private static final LocalDate VALID_START = java.sql.Date.valueOf("2022-11-08").toLocalDate();
    private static final LocalDate VALID_END= java.sql.Date.valueOf("2023-11-10").toLocalDate();

    private static final String NEW_NAME = "BAY DAYS AT THE BAY";

    private static final String CATEGORY_NAME = "Adventure";
    private static final String CATEGORY_DESC = "Story based RPG";

    private static final String GAME_NAME = "Zelda Raid Shadow Legends";
    private static final String GAME_DESCRIPTION = "A Zelda walks into a bar";
    private static final float GAME_PRICE = 19.99f;
    private static final int GAME_QUANTITY = 25;
    private static final LocalDate GAME_DATE = java.sql.Date.valueOf("2020-11-08").toLocalDate();

    private int offerId;
    private int videoGameId;
    private int categoryId;

    @AfterAll
    public void clearDatabase() {
        offerRepo.deleteAll();
        videoGameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidOffer() {
        // First create a category, then save the category ID
        CategoryRequestDto categoryRequest = new CategoryRequestDto(CATEGORY_NAME, CATEGORY_DESC);

        ResponseEntity<CategoryResponseDto> categoryResponse = client.postForEntity("/category", categoryRequest, CategoryResponseDto.class);
        assertNotNull(categoryResponse);
        assertEquals(CATEGORY_NAME, categoryResponse.getBody().getName());
        assertEquals(CATEGORY_DESC, categoryResponse.getBody().getDescription());
        categoryId = categoryResponse.getBody().getId();

        // Then create a video game, and save the video game ID
        VideoGameRequestDto videoGameRequest = new VideoGameRequestDto(GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY, GAME_DATE, categoryId);

        ResponseEntity<VideoGameResponseDto> videoGameResponse = client.postForEntity("/videogame", videoGameRequest, VideoGameResponseDto.class);
        assertNotNull(videoGameResponse);
        assertEquals(GAME_NAME, videoGameResponse.getBody().getName());
        assertEquals(GAME_DESCRIPTION, videoGameResponse.getBody().getDescription());
        assertEquals(GAME_PRICE, videoGameResponse.getBody().getPrice());
        assertEquals(GAME_QUANTITY, videoGameResponse.getBody().getQuantity());
        assertEquals(GAME_DATE, videoGameResponse.getBody().getDate());
        assertEquals(categoryId, videoGameResponse.getBody().getCategory().getId()); 
        videoGameId = videoGameResponse.getBody().getId();

        // Arrange
        OfferRequestDto offerRequest = new OfferRequestDto(VALID_NAME, VALID_DESCRIPTION, VALID_EFFECT, VALID_START, VALID_END, videoGameId);

        // Act
        ResponseEntity<OfferResponseDto> offerResponse = client.postForEntity("/offer", offerRequest, OfferResponseDto.class);
        assertNotNull(offerResponse);
        assertEquals(HttpStatus.OK, offerResponse.getStatusCode());
        assertEquals(VALID_NAME, offerResponse.getBody().getName());
        assertEquals(VALID_DESCRIPTION, offerResponse.getBody().getDescription());
        assertEquals(VALID_EFFECT, offerResponse.getBody().getEffect());
        assertEquals(VALID_START, offerResponse.getBody().getStartDate());
        assertEquals(VALID_END, offerResponse.getBody().getEndDate());
        assertEquals(videoGameId, offerResponse.getBody().getVideoGameId()); 
        offerId = offerResponse.getBody().getId();       
    }

    @Test
    @Order(2)
    public void testCreateInvalidOffer() {
        // Arrange
        OfferRequestDto offerRequest = new OfferRequestDto(null, VALID_DESCRIPTION, VALID_EFFECT, VALID_START, VALID_END, videoGameId);

        // Act 
          // Act
          ResponseEntity<OfferResponseDto> offerResponse = client.postForEntity("/offer", offerRequest, OfferResponseDto.class);
          assertNotNull(offerResponse);
          assertEquals(HttpStatus.BAD_REQUEST, offerResponse.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetValidOffer() {
        // Arrange
        String url = String.format("/offer/%d", this.offerId);

        // Act
        ResponseEntity<OfferResponseDto> response = client.getForEntity(url, OfferResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_NAME, response.getBody().getName());
        assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
        assertEquals(VALID_EFFECT, response.getBody().getEffect());
        assertEquals(VALID_START, response.getBody().getStartDate());
        assertEquals(VALID_END, response.getBody().getEndDate());
        assertEquals(videoGameId, response.getBody().getVideoGameId()); 
    }
    
    @Test
    @Order(4)
    public void testGetInvalidOffer() {
        // Arrange
        String url = String.format("/offer/%d", this.offerId + 1);

        // Act
        ResponseEntity<OfferResponseDto> response = client.getForEntity(url, OfferResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testEditOfferMissingParameters() {
        // Arrange
        String url = String.format("/offer/%d", this.offerId);
        OfferRequestDto offerRequest = new OfferRequestDto("A", VALID_DESCRIPTION, VALID_EFFECT, VALID_START, VALID_END, videoGameId);

        // Act
        client.put(url, offerRequest);
        ResponseEntity<OfferResponseDto> response = client.getForEntity(url, OfferResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_NAME, response.getBody().getName());
        assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
        assertEquals(VALID_EFFECT, response.getBody().getEffect());
        assertEquals(VALID_START, response.getBody().getStartDate());
        assertEquals(VALID_END, response.getBody().getEndDate());
        assertEquals(videoGameId, response.getBody().getVideoGameId());
    }

    @Test
    @Order(6)
    public void testEditOffer() {
        // Arrange
        String url = String.format("/offer/%d", this.offerId);
        OfferRequestDto offerRequest = new OfferRequestDto(NEW_NAME, VALID_DESCRIPTION, VALID_EFFECT, VALID_START, VALID_END, videoGameId);
        
        // Act
        client.put(url, offerRequest);
        ResponseEntity<OfferResponseDto> response = client.getForEntity(url, OfferResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NEW_NAME, response.getBody().getName());
        assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
        assertEquals(VALID_EFFECT, response.getBody().getEffect());
        assertEquals(VALID_START, response.getBody().getStartDate());
        assertEquals(VALID_END, response.getBody().getEndDate());
        assertEquals(videoGameId, response.getBody().getVideoGameId());

    }

    @Test
    @Order(7)
    public void testDeleteNonexistingOffer() {
        // Arrange
        String invalidOfferUrl = String.format("/offer/%d", this.offerId + 1);
        String validOfferUrl = String.format("/offer/%d", this.offerId);


        // Act
        client.delete(invalidOfferUrl);
        ResponseEntity<OfferResponseDto> response = client.getForEntity(validOfferUrl, OfferResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NEW_NAME, response.getBody().getName());
        assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
        assertEquals(VALID_EFFECT, response.getBody().getEffect());
        assertEquals(VALID_START, response.getBody().getStartDate());
        assertEquals(VALID_END, response.getBody().getEndDate());
        assertEquals(videoGameId, response.getBody().getVideoGameId());
    }

    @Test
    @Order(8)
    public void testDeleteOffer() {
        // Arrange
        String url = String.format("/offer/%d", this.offerId);

        // Act
        client.delete(url);
        ResponseEntity<OfferResponseDto> response = client.getForEntity(url, OfferResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
}
