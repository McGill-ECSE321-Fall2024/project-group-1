package ca.mcgill.ecse321group1.gamestore.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)



public class CategoryIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CategoryRepository categoryRepo;

    private static final String VALID_NAME = "Adventure";
    private static final String VALID_DESC = "Story based RPG";
    private static final String NEW_NAME = "Action";
    private static final String NEW_DESC = "Fight till the death";

    private int categoryId;

    @AfterAll
    public void clearDatabase() {
        categoryRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidCategory() {
        // Arrange
        CategoryRequestDto request = new CategoryRequestDto(VALID_NAME, VALID_DESC);

        // Act
        ResponseEntity<CategoryResponseDto> response = client.postForEntity("/category", request, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_NAME, response.getBody().getName());
        assertEquals(VALID_DESC, response.getBody().getDescription());
        assertTrue(response.getBody().getId() > 0, "The ID should be positive.");
        categoryId = response.getBody().getId();

    }

    @Test
    @Order(2)
    public void testCreateInvalidNameCategory() {
        // Arrange
        CategoryRequestDto request = new CategoryRequestDto(null, VALID_DESC);

        // Act
        ResponseEntity<CategoryResponseDto> response = client.postForEntity("/category", request, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        String body = response.getBody().getName();

    }

    @Test
    @Order(3)
    public void testGetValidCategory() {
        // Arrange
        String url = String.format("/category/%d", this.categoryId);

        // Act
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(url, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.categoryId, response.getBody().getId());
        assertEquals(VALID_NAME, response.getBody().getName());
        assertEquals(VALID_DESC, response.getBody().getDescription());
    }

    @Test 
    @Order(4)
    public void testGetInvalidCategory() {
        // Arrange
        String url = String.format("/category/%d", this.categoryId + 1);

        // Act
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(url, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody().getName());
        assertEquals(null, response.getBody().getDescription());
 
    }

    @Test
    @Order(5)
    public void testEditInvalidCategory() {
        // Arrange
        String url = String.format("/category/%d", this.categoryId);
        CategoryRequestDto request = new CategoryRequestDto(null, NEW_DESC);

        // Act
        client.put(url, request);
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(url, CategoryResponseDto.class);

        // Assert 
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_NAME, response.getBody().getName());
        assertEquals(VALID_DESC, response.getBody().getDescription());
    }

    @Test
    @Order(6)
    public void testEditValidCategory() {
        // Arrange
        String url = String.format("/category/%d", this.categoryId);
        CategoryRequestDto request = new CategoryRequestDto(NEW_NAME, NEW_DESC);

        // Act 
        client.put(url, request);
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(url, CategoryResponseDto.class);

        // Assert 
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NEW_NAME, response.getBody().getName());
        assertEquals(NEW_DESC, response.getBody().getDescription());
    }

    @Test
    @Order(7)
    public void testDeleteInvalidCategory() {
        // Arrange
        String invalidUrl = String.format("/category/%d", this.categoryId + 1);
        String validUrl = String.format("/category/%d", this.categoryId);

        // Act
        client.delete(invalidUrl);
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(validUrl, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NEW_NAME, response.getBody().getName());
        assertEquals(NEW_DESC, response.getBody().getDescription());
    }

    @Test
    @Order(8)
    public void testDeleteValidCategory() {
        // Arrange
        String url = String.format("/category/%d", this.categoryId);

        // Act
        client.delete(url);
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(url, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
}
