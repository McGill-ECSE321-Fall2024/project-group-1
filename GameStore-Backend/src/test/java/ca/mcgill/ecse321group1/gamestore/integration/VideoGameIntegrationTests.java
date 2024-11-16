package ca.mcgill.ecse321group1.gamestore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class VideoGameIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private VideoGameRepository videoGameRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    private static final String VALID_NAME = "Zelda Raid Shadow Legends";
    private static final String VALID_DESCRIPTION = "A Zelda walks into a bar";
    private static final float VALID_PRICE = 19.99f;
    private static final int VALID_QUANTITY = 25;
    private static final LocalDate VALID_DATE = java.sql.Date.valueOf("2023-11-08").toLocalDate();

    private static final String NEW_NAME = "Apex Legends Fortnite Collab";
    private static final String NEW_DESCRIPTION = "Battle royale battle bus";
    private static final float NEW_PRICE = 79.99f;
    private static final LocalDate NEW_DATE = java.sql.Date.valueOf("2023-12-12").toLocalDate();

    private static final String VALID_CATEGORY_NAME = "Adventure";
    private static final String VALID_CATEGORY_DESC = "Story based RPG";

    private int videoGameId;
    private int categoryId;

    @AfterAll
    public void clearDatabase() {
        videoGameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidVideoGame() {
        // first create category, then feed that into video game
        CategoryRequestDto categoryRequest = new CategoryRequestDto(VALID_CATEGORY_NAME, VALID_CATEGORY_DESC);

        ResponseEntity<CategoryResponseDto> categoryResponse = client.postForEntity("/category", categoryRequest, CategoryResponseDto.class);
        assertNotNull(categoryResponse);
        assertEquals(VALID_CATEGORY_NAME, categoryResponse.getBody().getName());
        assertEquals(VALID_CATEGORY_DESC, categoryResponse.getBody().getDescription());
        categoryId = categoryResponse.getBody().getId();
        
        // Arrange
        VideoGameRequestDto videoGameRequest = new VideoGameRequestDto(VALID_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_DATE, categoryResponse.getBody().getId());

        // Act
        ResponseEntity<VideoGameResponseDto> response = client.postForEntity("/videogame", videoGameRequest, VideoGameResponseDto.class);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_NAME, response.getBody().getName());
        assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
        assertEquals(VALID_PRICE, response.getBody().getPrice());
        assertEquals(VALID_QUANTITY, response.getBody().getQuantity());
        assertEquals(VALID_DATE, response.getBody().getDate());
        assertEquals("Pending", response.getBody().getStatus().toString());   
        videoGameId = response.getBody().getId();
    }

    @Test
    @Order(2)
    public void testCreateInvalidVideoGame() {
        // Arrange
        VideoGameRequestDto videoGameRequest = new VideoGameRequestDto(null, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_DATE, categoryId);

        // Act
        ResponseEntity<VideoGameResponseDto> response = client.postForEntity("/videogame", videoGameRequest, VideoGameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetValidVideoGame() {
        // Arrange
        String url = String.format("/videogame/%d", this.videoGameId);

        // Act 
        ResponseEntity<VideoGameResponseDto> response = client.getForEntity(url, VideoGameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_NAME, response.getBody().getName());
        assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
        assertEquals(VALID_PRICE, response.getBody().getPrice());
        assertEquals(VALID_QUANTITY, response.getBody().getQuantity());
        assertEquals(VALID_DATE, response.getBody().getDate());
        assertEquals("Pending", response.getBody().getStatus().toString());  
    }

    @Test
    @Order(4)
    public void testGetInvalidVideoGame() {
        // Arrange
        String url = String.format("/videogame/%d", this.videoGameId + 1);

        // Act
        ResponseEntity<VideoGameResponseDto> response = client.getForEntity(url, VideoGameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testEditInvalidVideoGame() {
        // Arrange
        String url = String.format("/videogame/%d", this.videoGameId);
        VideoGameRequestDto request = new VideoGameRequestDto(null, NEW_DESCRIPTION, NEW_PRICE, VALID_QUANTITY, NEW_DATE, categoryId);

        // Act 
        client.put(url, request);
        ResponseEntity<VideoGameResponseDto> response = client.getForEntity(url, VideoGameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_NAME, response.getBody().getName());
        assertEquals(VALID_DESCRIPTION, response.getBody().getDescription());
        assertEquals(VALID_PRICE, response.getBody().getPrice());
        assertEquals(VALID_QUANTITY, response.getBody().getQuantity());
        assertEquals(VALID_DATE, response.getBody().getDate());
        assertEquals("Pending", response.getBody().getStatus().toString());  
    }

    @Test
    @Order(6)
    public void testEditValidVideoGame() {
        // Arrange
        String url = String.format("/videogame/%d", this.videoGameId);
        VideoGameRequestDto request = new VideoGameRequestDto(NEW_NAME, NEW_DESCRIPTION, NEW_PRICE, VALID_QUANTITY, NEW_DATE, categoryId);
        
        // Act 
        client.put(url, request);
        ResponseEntity<VideoGameResponseDto> response = client.getForEntity(url, VideoGameResponseDto.class);


        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NEW_NAME, response.getBody().getName());
        assertEquals(NEW_DESCRIPTION, response.getBody().getDescription());
        assertEquals(NEW_PRICE, response.getBody().getPrice());
        assertEquals(VALID_QUANTITY, response.getBody().getQuantity());
        assertEquals(NEW_DATE, response.getBody().getDate());
        assertEquals("Pending", response.getBody().getStatus().toString());  

    }
}