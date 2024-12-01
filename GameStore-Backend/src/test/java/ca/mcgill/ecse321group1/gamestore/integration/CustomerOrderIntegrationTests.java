package ca.mcgill.ecse321group1.gamestore.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321group1.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.CustomerOrderCreationDto;
import ca.mcgill.ecse321group1.gamestore.dto.CustomerOrderListDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerOrderRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OfferRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;

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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)

public class CustomerOrderIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private VideoGameRepository videoGameRepo;
    @Autowired
    private CategoryRepository categoryRepo;   
    @Autowired
    private CustomerOrderRepository customerOrderRepo;
    @Autowired
    private OfferRepository offerRepo;

    private static final String CATEGORY_NAME = "Adventure";
    private static final String CATEGORY_DESC = "Story based RPG";

    private static final String GAME_1_NAME = "Zelda Raid Shadow Legends";
    private static final String GAME_1_DESCRIPTION = "A Zelda walks into a bar";
    private static final float GAME_1_PRICE = 20.00f;
    private static final int GAME_1_QUANTITY = 25;
    private static final LocalDate GAME_1_DATE = java.sql.Date.valueOf("2023-11-08").toLocalDate();

    private static final String GAME_2_NAME = "Fortnite Save the World";
    private static final String GAME_2_DESCRIPTION = "Game still in beta";
    private static final float GAME_2_PRICE = 21.00f;
    private static final int GAME_2_QUANTITY = 25;
    private static final LocalDate GAME_2_DATE = java.sql.Date.valueOf("2002-11-09").toLocalDate();

    private static final String CUST_USERNAME = "Jonathan";
    private static final String CUST_EMAIL = "Jonathan@outlook.com";
    private static final String CUST_PASSWORD = "ILoveFortnite2003";
    private static final String CUST_ADDRESS = "123 Sesame Street, New York, New York, USA, 123456";
    private static final String CUST_PHONE_NUMBER = "604604604";

    private static final String SHIPPING_ADDRESS = "1444 St Mathieu St, Montreal, Quebec H3H 2H9";

    private int categoryId;
    private int videoGame1Id;
    private int videoGame2Id;
    private int customerId;
    private int sharedId;
    private int customerOrder1Id;
    private int customerOrder2Id;

    @AfterAll
    public void clearDatabase() {
        offerRepo.deleteAll();
        customerOrderRepo.deleteAll();
        customerRepo.deleteAll();
        videoGameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateCustomerOrder() {
        // First create category 
        CategoryRequestDto categoryRequest = new CategoryRequestDto(CATEGORY_NAME, CATEGORY_DESC);

        ResponseEntity<CategoryResponseDto> categoryResponse = client.postForEntity("/category", categoryRequest, CategoryResponseDto.class);
        assertNotNull(categoryResponse);
        assertEquals(CATEGORY_NAME, categoryResponse.getBody().getName());
        assertEquals(CATEGORY_DESC, categoryResponse.getBody().getDescription());
        categoryId = categoryResponse.getBody().getId();

        // Second create 2 video games

        VideoGameRequestDto videoGame1Request = new VideoGameRequestDto(GAME_1_NAME, GAME_1_DESCRIPTION, GAME_1_PRICE, GAME_1_QUANTITY, GAME_1_DATE, this.categoryId);
        VideoGameRequestDto videoGame2Request = new VideoGameRequestDto(GAME_2_NAME, GAME_2_DESCRIPTION, GAME_2_PRICE, GAME_2_QUANTITY, GAME_2_DATE, this.categoryId);
        
        ResponseEntity<VideoGameResponseDto> videoGame1Response = client.postForEntity("/videogame", videoGame1Request, VideoGameResponseDto.class);
        ResponseEntity<VideoGameResponseDto> videoGame2Response = client.postForEntity("/videogame", videoGame2Request, VideoGameResponseDto.class);

        assertNotNull(videoGame1Response);
        assertEquals(HttpStatus.OK, videoGame1Response.getStatusCode());
        assertEquals(GAME_1_NAME, videoGame1Response.getBody().getName());
        assertEquals(GAME_1_DESCRIPTION, videoGame1Response.getBody().getDescription());
        assertEquals(GAME_1_PRICE, videoGame1Response.getBody().getPrice());
        assertEquals(GAME_1_QUANTITY, videoGame1Response.getBody().getQuantity());
        assertEquals(GAME_1_DATE, videoGame1Response.getBody().getDate());
        assertEquals("Pending", videoGame1Response.getBody().getStatus().toString());   
        videoGame1Id = videoGame1Response.getBody().getId();

        assertNotNull(videoGame2Response);
        assertEquals(HttpStatus.OK, videoGame2Response.getStatusCode());
        assertEquals(GAME_2_NAME, videoGame2Response.getBody().getName());
        assertEquals(GAME_2_DESCRIPTION, videoGame2Response.getBody().getDescription());
        assertEquals(GAME_2_PRICE, videoGame2Response.getBody().getPrice());
        assertEquals(GAME_2_QUANTITY, videoGame2Response.getBody().getQuantity());
        assertEquals(GAME_2_DATE, videoGame2Response.getBody().getDate());
        assertEquals("Pending", videoGame2Response.getBody().getStatus().toString());   
        videoGame2Id = videoGame2Response.getBody().getId();

        // Third create customer
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

        // Fourth add both video games to customer cart
        String game1CartUrl = String.format("/customer/%d/cart/%d/quantity/%d", this.customerId, this.videoGame1Id, 1);
        String game2CartUrl = String.format("/customer/%d/cart/%d/quantity/%d", this.customerId, this.videoGame2Id, 1);

        ResponseEntity<PersonResponseDto> game1CartResponse = client.postForEntity(game1CartUrl, null, PersonResponseDto.class);
        ResponseEntity<PersonResponseDto> game2CartResponse = client.postForEntity(game2CartUrl, null, PersonResponseDto.class);

        assertNotNull(game1CartResponse);
        assertEquals(HttpStatus.OK, game1CartResponse.getStatusCode());
        assertEquals(this.customerId,game1CartResponse.getBody().getId());
        assertEquals(CUST_USERNAME, game1CartResponse.getBody().getUsername());
        assertEquals(CUST_EMAIL, game1CartResponse.getBody().getEmail());
        assertEquals(CUST_ADDRESS, game1CartResponse.getBody().getAddress());
        assertEquals(CUST_PHONE_NUMBER, game1CartResponse.getBody().getPhoneNumber());
        assertEquals(1, game1CartResponse.getBody().getCart().size());
        assertEquals(videoGame1Id, game1CartResponse.getBody().getCart().get(0).getId());

        assertNotNull(game2CartResponse);
        assertEquals(HttpStatus.OK, game2CartResponse.getStatusCode());
        assertEquals(this.customerId,game2CartResponse.getBody().getId());
        assertEquals(CUST_USERNAME, game2CartResponse.getBody().getUsername());
        assertEquals(CUST_EMAIL, game2CartResponse.getBody().getEmail());
        assertEquals(CUST_ADDRESS, game2CartResponse.getBody().getAddress());
        assertEquals(CUST_PHONE_NUMBER, game2CartResponse.getBody().getPhoneNumber());
        assertEquals(2, game2CartResponse.getBody().getCart().size());
        assertEquals(videoGame2Id, game2CartResponse.getBody().getCart().get(1).getId());
        // Last create customer order
        // Arrange
        String url = String.format("/customer/%d/order", this.customerId);
        CustomerOrderCreationDto request = new CustomerOrderCreationDto(this.customerId, SHIPPING_ADDRESS);

        // Act
        ResponseEntity<CustomerOrderListDto> response = client.postForEntity(url, request, CustomerOrderListDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getOrders().size());
        assertEquals(response.getBody().getOrders().get(0).getSharedId(), response.getBody().getOrders().get(1).getSharedId());
        sharedId = response.getBody().getOrders().get(0).getSharedId();
        assertTrue((videoGame1Id == response.getBody().getOrders().get(0).getPurchased().getId()) || (videoGame1Id == response.getBody().getOrders().get(1).getPurchased().getId()));
        assertTrue((videoGame2Id == response.getBody().getOrders().get(0).getPurchased().getId()) || (videoGame2Id == response.getBody().getOrders().get(1).getPurchased().getId()));
        customerOrder1Id = response.getBody().getOrders().get(0).getId();
        customerOrder2Id = response.getBody().getOrders().get(1).getId();

    }

    @Test
    @Order(2)
    public void testGetCustomerOrdersBySharedId() {
        // Arrange
        String url = String.format("/order/%d", this.sharedId);

        // Act
        ResponseEntity<CustomerOrderListDto> response = client.getForEntity(url, CustomerOrderListDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((customerOrder1Id == response.getBody().getOrders().get(0).getId()) || (customerOrder1Id == response.getBody().getOrders().get(1).getId()));
        assertTrue((customerOrder2Id == response.getBody().getOrders().get(0).getId()) || (customerOrder2Id == response.getBody().getOrders().get(1).getId()));
        assertTrue((videoGame1Id == response.getBody().getOrders().get(0).getPurchased().getId()) || (videoGame1Id == response.getBody().getOrders().get(1).getPurchased().getId()));
        assertTrue((videoGame2Id == response.getBody().getOrders().get(0).getPurchased().getId()) || (videoGame2Id == response.getBody().getOrders().get(1).getPurchased().getId()));
    }

    @Test
    @Order(3)
    public void testGetOrderTotalPrice() {
        // Arrange
        String url = String.format("/order/%d/price", this.sharedId);

        // Act
        ResponseEntity<Float> response = client.getForEntity(url, Float.class);

        // Assert
        assertNotNull(response);
        assertEquals(41.00f, response.getBody());
    }

    @Test
    @Order(4)
    public void testGetUnsatisfiedOrder() {
        // Arrange
        String url = String.format("/videogame/%d/unsatisfied", this.videoGame1Id);

        // Act
        ResponseEntity<CustomerOrderListDto> response = client.getForEntity(url, CustomerOrderListDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getOrders().size());
        assertEquals(videoGame1Id, response.getBody().getOrders().get(0).getPurchased().getId());
    }

    @Test
    @Order(5)
    public void testSatisfyOrder() {
        // First get video game to satisfy
        String fetchUnsatUrl = String.format("/videogame/%d/unsatisfied", this.videoGame1Id);
        ResponseEntity<CustomerOrderListDto> videoGameToSatisfyResponse = client.getForEntity(fetchUnsatUrl, CustomerOrderListDto.class);

        assertNotNull(videoGameToSatisfyResponse);
        assertEquals(HttpStatus.OK, videoGameToSatisfyResponse.getStatusCode());
        assertEquals(1, videoGameToSatisfyResponse.getBody().getOrders().size());
        assertEquals(videoGame1Id, videoGameToSatisfyResponse.getBody().getOrders().get(0).getPurchased().getId());

        // Arrange
        CustomerOrderListDto videoGameToSatisfy = videoGameToSatisfyResponse.getBody();


        // Act
        client.put("/order/update", videoGameToSatisfy);
        ResponseEntity<CustomerOrderListDto> response = client.getForEntity(fetchUnsatUrl, CustomerOrderListDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getOrders().size());
    }

    @Test
    @Order(6)
    public void testGetOrderString() {
        // Arrange
        String url = String.format("/customer/%d/pastorders", this.sharedId);
        
        // Act
        ResponseEntity<String> response = client.getForEntity(url, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDeleteOrdersBySharedId() {
        // Arrange
        String url = String.format("/order/%d", this.sharedId);

        // Act
        client.delete(url);
        ResponseEntity<CustomerOrderListDto> response = client.getForEntity(url, CustomerOrderListDto.class);


        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
