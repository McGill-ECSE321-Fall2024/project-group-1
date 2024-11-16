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
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OfferRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.OfferRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.OfferResponseDto;



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

    private static final String VALID_NAME = "Half off deal";
    private static final String VALID_DESCRIPTION = "Pay only half the sticker price";
    private static final String VALID_EFFECT = "50%";
    private static final LocalDate VALID_START = java.sql.Date.valueOf("2023-11-08").toLocalDate();
    private static final LocalDate VALID_END= java.sql.Date.valueOf("2023-11-10").toLocalDate();

    private static final String VALID_CATEGORY_NAME = "Adventure";
    private static final String VALID_CATEGORY_DESC = "Story based RPG";

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
        CategoryRequestDto categoryRequest = new CategoryRequestDto(VALID_CATEGORY_NAME, VALID_CATEGORY_DESC);

        ResponseEntity<CategoryResponseDto> categoryResponse = client.postForEntity("/category", categoryRequest, CategoryResponseDto.class);
        assertNotNull(categoryResponse);
        assertEquals(VALID_CATEGORY_NAME, categoryResponse.getBody().getName());
        assertEquals(VALID_CATEGORY_DESC, categoryResponse.getBody().getDescription());
        categoryId = categoryResponse.getBody().getId();

        // Then create a video game, and save the video game ID


        OfferRequestDto request = new OfferRequestDto(VALID_NAME, VALID_DESCRIPTION, VALID_EFFECT, VALID_START, VALID_END, videoGameId);
        
    }
    
}
