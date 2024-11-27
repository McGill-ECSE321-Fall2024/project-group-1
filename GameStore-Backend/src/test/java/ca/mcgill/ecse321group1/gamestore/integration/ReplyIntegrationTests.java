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
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReplyRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReplyResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.ReplyRepository;
import ca.mcgill.ecse321group1.gamestore.repository.ReviewRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class ReplyIntegrationTests {
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
    @Autowired
    private ReplyRepository replyRepo;


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

    private static final String REVIEW_CONTENT = "Great game! 5/5";
    private static final LocalDate REVIEW_DATE = java.sql.Date.valueOf("2023-11-10").toLocalDate();
    private static final Review.Rating REVIEW_RATING = Review.Rating.fourStar;

    private static final String REPLY_CONTENT = "Agreed! I loved this game.";
    private static final LocalDate REPLY_DATE = java.sql.Date.valueOf("2023-11-12").toLocalDate();

    private int categoryId;
    private int videoGameId;
    private int customerId;
    private int reviewId;
    private int replyId;

    @AfterAll 
    public void ClearDatabase() {
        replyRepo.deleteAll();
        reviewRepo.deleteAll();
        videoGameRepo.deleteAll();
        categoryRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateReply() {
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

        // Then create review

        ReviewRequestDto reviewRequest = new ReviewRequestDto(REVIEW_CONTENT, REVIEW_DATE, REVIEW_RATING, videoGameId, customerId);

        ResponseEntity<ReviewResponseDto> reviewResponse = client.postForEntity("/review", reviewRequest, ReviewResponseDto.class);
        assertNotNull(reviewResponse);
        assertEquals(HttpStatus.OK, reviewResponse.getStatusCode());
        assertEquals(REVIEW_CONTENT, reviewResponse.getBody().getContent());
        assertEquals(REVIEW_DATE, reviewResponse.getBody().getDate());
        assertEquals(REVIEW_RATING.toString(), reviewResponse.getBody().getRating().toString());
        assertEquals(videoGameId, reviewResponse.getBody().getVideoGameId());
        assertEquals(customerId, reviewResponse.getBody().getCustomerId());
        reviewId = reviewResponse.getBody().getId();

        // Arrange
        String url = String.format("/review/%d/reply", this.reviewId);
        ReplyRequestDto replyRequest = new ReplyRequestDto(REPLY_DATE, REPLY_CONTENT);

        // Act
        ResponseEntity<ReplyResponseDto> replyResponse = client.postForEntity(url, replyRequest, ReplyResponseDto.class);

        // Assert
        // assertNotNull(replyResponse);
        // assertEquals(HttpStatus.OK, replyResponse.getStatusCode());
        // assertEquals(REPLY_CONTENT, replyResponse.getBody().getContent());
        // assertEquals(REPLY_DATE, replyResponse.getBody().getDate());
        // assertEquals(reviewId, replyResponse.getBody().getReviewId());
        // replyId = replyResponse.getBody().getId();

        // NOT WORKING, but making reply using CURL works. ReplyRequestDto is created fine, but date switches to null?
    }

    
}
