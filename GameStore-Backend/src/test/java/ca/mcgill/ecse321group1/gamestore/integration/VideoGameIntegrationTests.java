package ca.mcgill.ecse321group1.gamestore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321group1.gamestore.service.CategoryService;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame.Status;



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

    private static final String VALID_NAME = "Zelda";
    private static final String VALID_DESCRIPTION = "A Zelda walks into a bar";
    private static final float VALID_PRICE = 19.99f;
    private static final int VALID_QUANTITY = 25;
    private static final Date VALID_DATE = java.sql.Date.valueOf("2023-11-08");

    private static final String VALID_CATEGORY_NAME = "Adventure";
    private static final String VALID_CATEGORY_DESC = "Story based RPG";

    private int videoGameId;
    private int categoryId;
    private Category category;

    @AfterAll
    public void clearDatabase() {
        videoGameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidVideoGame() {
        // first create category, then feed that into video game
        // Arrange
        CategoryRequestDto categoryRequest = new CategoryRequestDto(VALID_CATEGORY_NAME, VALID_CATEGORY_DESC);

        // Act
        ResponseEntity<CategoryResponseDto> categoryResponse = client.postForEntity("/category", categoryRequest, CategoryResponseDto.class);
        categoryId = categoryResponse.getBody().getId();
        category = new Category(categoryId, VALID_CATEGORY_NAME, VALID_CATEGORY_DESC);
        
        VideoGameRequestDto videoGameRequest = new VideoGameRequestDto(VALID_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_DATE, category);
        ResponseEntity<VideoGameResponseDto> videoGameResponse = client.postForEntity("/videogame", videoGameRequest, VideoGameResponseDto.class);
        
        //Assert
        assertNotNull(videoGameResponse);
        assertEquals(HttpStatus.OK, videoGameResponse.getStatusCode());

    }
}