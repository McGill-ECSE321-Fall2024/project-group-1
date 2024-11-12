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

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import ca.mcgill.ecse321group1.gamestore.service.CategoryService;
import ca.mcgill.ecse321group1.gamestore.dto.Status;

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


    //make sure we are autowiring all the repos that are called

    
    private static final int VALID_ID = 123;
    private static final String VALID_NAME = "Zelda";
    private static final String VALID_DESCRIPTION = "A Zelda walks into a bar";
    private static final float VALID_PRICE = 19.99f;
    private static final int VALID_QUANTITY = 25;
    private static final Date VALID_DATE = java.sql.Date.valueOf("2023-11-08");

    private static final String VALID_CATEGORY_NAME = "Adventure";
    private static final String VALID_CATEGORY_DESC = "Story based RPG";
    //private static final Category VALID_CATEGORY = categoryService.createCategory("Action", "Fighting game");
    //private static final Category VALID_CATEGORY = null;

    private int videoGameId;

    @BeforeAll
    @AfterAll
    public void clearDatabase() {
        videoGameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    public void testCreateValidVideoGame() {
        // not sure if this is the right way to do this.
        //CategoryService categoryService = new CategoryService();
        //Category VALID_CATEGORY = categoryService.createCategory("Adventure", "Role play as a character to beat story based quests");

        // Arrange 
        CategoryRequestDto adventure = new CategoryRequestDto(VALID_CATEGORY_NAME, VALID_CATEGORY_DESC); // Makes a dto to create category
        ResponseEntity<CategoryResponseDto> categoryResponse = client.postForEntity("/category", adventure, CategoryResponseDto.class);

        VideoGameRequestDto zelda = new VideoGameRequestDto(VALID_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_DATE, null);
        assertNotNull(zelda);
        // Act 

        ResponseEntity<VideoGameResponseDto> response = client.postForEntity("/videogame", zelda, VideoGameResponseDto.class);

        // Assert
        assertNotNull(response);

        // Quantity should be over 0
        // ?ID should be over 0?
    }
    /* 

    // NEED TO CREATE VIDEO GAME FIRST THEN RUN THE GET BY ID TEST.
    @Test
    public void testGetValidVideoGameById() {
        // Arrange 
        String url = String.format("/videogame/%d", this.videoGameId);

        System.out.println(String.format("URL: %s", url));

        // Act
        ResponseEntity<VideoGameResponseDto> response = client.getForEntity(url, VideoGameResponseDto.class);

        // Assert 
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.videoGameId, response.getBody().getId());
        assertEquals(VALID_ID, response.getBody().getId());
    }

    // TESTING INVALID VIDEO GAME ID AND RETURNING PROPER ERROR CODE
    @Test
    public void testGetInvalidVideoGame() {
        //Arrange

    }
    */

}