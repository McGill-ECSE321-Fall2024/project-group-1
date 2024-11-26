package ca.mcgill.ecse321group1.gamestore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReviewListDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.ReviewRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class ReviewIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private VideoGameRepository videoGameRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ReviewRepository reviewRepo;

    private static final String CATEGORY_NAME = "Adventure";
    private static final String CATEGORY_DESC = "Story based RPG";

    private static final String GAME_NAME = "Zelda Raid Shadow Legends";
    private static final String GAME_DESCRIPTION = "A Zelda walks into a bar";
    private static final float GAME_PRICE = 19.99f;
    private static final int GAME_QUANTITY = 25;
    private static final LocalDate GAME_DATE = java.sql.Date.valueOf("2023-11-08").toLocalDate();

    private static final String CUST_USERNAME = "Jonathan";
    private static final String CUST_EMAIL = "Jonathan@outlook.com";
    private static final String CUST_PASSWORD = "ILoveFortnite2003";
    private static final String CUST_ADDRESS = "123 Sesame Street, New York, New York, USA, 123456";
    private static final String CUST_PHONE_NUMBER = "604604604";

    private static final String VALID_CONTENT = "Great game! 5/5";
    private static final LocalDate VALID_DATE = java.sql.Date.valueOf("2023-11-10").toLocalDate();
    private static final Review.Rating VALID_RATING = Review.Rating.fourStar;

    private static final String NEW_CONTENT = "Terrible game! 1/5";
    private static final LocalDate NEW_DATE = java.sql.Date.valueOf("2023-11-13").toLocalDate();
    private static final Review.Rating NEW_RATING = Review.Rating.oneStar; 

    private int categoryId;
    private int videoGameId;
    private int customerId;
    private int reviewId;

    @AfterAll // Might throw error with bidirectional associations unfortunately. Might have to change order of delete also
    public void ClearDatabase() {
        reviewRepo.deleteAll();
        videoGameRepo.deleteAll();
        categoryRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateReview() {
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

        // Then create customer
        PersonRequestDto custRequest = new PersonRequestDto(CUST_USERNAME, CUST_EMAIL, CUST_PASSWORD, CUST_ADDRESS, CUST_PHONE_NUMBER);

        ResponseEntity<PersonResponseDto> custResponse = client.postForEntity("/customer", custRequest, PersonResponseDto.class);
        assertNotNull(custResponse);
        assertEquals(HttpStatus.OK, custResponse.getStatusCode());
        PersonResponseDto custHelper = custResponse.getBody();

        assertEquals(CUST_USERNAME, custHelper.getUsername());
        assertEquals(CUST_EMAIL, custHelper.getEmail());
        assertEquals(CUST_ADDRESS, custHelper.getAddress());
        assertEquals(CUST_PHONE_NUMBER, custHelper.getPhoneNumber());
        customerId = custHelper.getId();

        // Arrange
        ReviewRequestDto request = new ReviewRequestDto(VALID_CONTENT, VALID_DATE, VALID_RATING, videoGameId, customerId);
        
        // Act
        ResponseEntity<ReviewResponseDto> response = client.postForEntity("/review", request, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_CONTENT, response.getBody().getContent());
        assertEquals(VALID_DATE, response.getBody().getDate());
        assertEquals(VALID_RATING.toString(), response.getBody().getRating().toString());
        assertEquals(videoGameId, response.getBody().getVideoGameId());
        assertEquals(customerId, response.getBody().getCustomerId());
        reviewId = response.getBody().getId();
        }

    @Test
    @Order(2)
    public void testCreateInvalidReview() {
        // Arrange
        ReviewRequestDto request = new ReviewRequestDto(VALID_CONTENT, VALID_DATE, VALID_RATING, videoGameId, customerId + 1);

        // Act
        ResponseEntity<ReviewResponseDto> response = client.postForEntity("/review", request, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetValidReview() {
        // Arrange
        String url = String.format("/review/%d", this.reviewId);

        // Act
        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_CONTENT, response.getBody().getContent());
        assertEquals(VALID_DATE, response.getBody().getDate());
        assertEquals(VALID_RATING.toString(), response.getBody().getRating().toString());
        assertEquals(videoGameId, response.getBody().getVideoGameId());
        assertEquals(customerId, response.getBody().getCustomerId());
    }

    @Test
    @Order(4)
    public void testGetInvalidReview() {
        // Arrange
        String url = String.format("/review/%d", this.reviewId + 1);

        // Act
        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testEditValidReview() {
        // Arrange 
        String url = String.format("/review/%d", this.reviewId);
        ReviewRequestDto request = new ReviewRequestDto(NEW_CONTENT, NEW_DATE, NEW_RATING, this.videoGameId, this.customerId);

        // Act
        client.put(url, request);
        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NEW_CONTENT, response.getBody().getContent());
        assertEquals(NEW_DATE, response.getBody().getDate());
        assertEquals(NEW_RATING.toString(), response.getBody().getRating().toString());
        assertEquals(videoGameId, response.getBody().getVideoGameId());
        assertEquals(customerId, response.getBody().getCustomerId());
    }

    @Test
    @Order(6)
    public void testEditInvalidReview() {
        // Arrange 
        String url = String.format("/review/%d", this.reviewId);
        ReviewRequestDto request = new ReviewRequestDto(null, NEW_DATE, NEW_RATING, this.videoGameId, this.customerId);

        // Act
        client.put(url, request);
        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NEW_CONTENT, response.getBody().getContent());
        assertEquals(NEW_DATE, response.getBody().getDate());
        assertEquals(NEW_RATING.toString(), response.getBody().getRating().toString());
        assertEquals(videoGameId, response.getBody().getVideoGameId());
        assertEquals(customerId, response.getBody().getCustomerId());
    }

    @Test
    @Order(7)
    public void testGetReviewsForValidVideoGameId() {
        // Arrange
        String url = String.format("/review/game/%d", this.videoGameId);
        
        // Act
        ResponseEntity<ReviewListDto> response = client.getForEntity(url, ReviewListDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getReviews().size());
        ReviewResponseDto matchingReview = response.getBody().getReviews().get(0);

        assertEquals(NEW_CONTENT, matchingReview.getContent());
        assertEquals(NEW_DATE, matchingReview.getDate());
        assertEquals(NEW_RATING.toString(), matchingReview.getRating().toString());    
    }

    @Test
    @Order(8)
    public void testGetReviewsForInvalidVideoGameId() {
        // Arrange
        String url = String.format("/review/game/%d", this.videoGameId + 1);
        
        // Act
        ResponseEntity<ReviewListDto> response = client.getForEntity(url, ReviewListDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getReviews().size());
    }

    
}
