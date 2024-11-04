package ca.mcgill.ecse321group1.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.CustomerOrder;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerOrderRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;

import java.sql.Date;
import java.util.*;

@SpringBootTest
public class CustomerOrderRepositoryTests {
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VideoGameRepository videoGameRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    public void clearDatabase() {
        customerOrderRepository.deleteAll();
        videoGameRepository.deleteAll();
        customerRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomerOrder() {
        // Create and Save Customer
        String username = "AlanBrotherton";
        String email = "alanbrotherton@example.com";
        String passwordHash = "password123";
        String address = "1600 Pennsylvania Avenue NW";
        String phoneNumber = "4703237795";

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPasswordHash(passwordHash);
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);
        customer = customerRepository.save(customer);

        // Create and Save Category
        String categoryName = "Action";
        String categoryDescription = "Action games";
        Category category = new Category();
        category.setName(categoryName);
        category.setDescription(categoryDescription);
        category = categoryRepository.save(category);

        // Create and Save VideoGame
        String gameName = "Game Title";
        String gameDescription = "Game Description";
        float gamePrice = 49.99f;
        int gameQuantity = 100;
        Date gameDate = new Date(System.currentTimeMillis());
        VideoGame.Status gameStatus = VideoGame.Status.Active;

        VideoGame videoGame = new VideoGame();
        videoGame.setName(gameName);
        videoGame.setDescription(gameDescription);
        videoGame.setPrice(gamePrice);
        videoGame.setQuantity(gameQuantity);
        videoGame.setDate(gameDate);
        videoGame.setStatus(gameStatus);
        videoGame.setCategory(category);
        videoGame = videoGameRepository.save(videoGame);

        // Create and Save Customer Order
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        Date date = new java.sql.Date(calendar.getTimeInMillis());
        float price = 79.99f;
        int quantity = 10;
        String offers = "20% Off!";
        String orderAddress = "7700 Bd Decarie";

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setDate(date);
        customerOrder.setPrice(price);
        customerOrder.setQuantity(quantity);
        customerOrder.setOffersApplied(offers);
        customerOrder.setAddress(orderAddress);
        customerOrder.setCustomer(customer);
        customerOrder.addPurchased(videoGame);
        customerOrder = customerOrderRepository.save(customerOrder);

        // Read Customer Order from database
        int id = customerOrder.getId();
        CustomerOrder customerOrderFromDb = customerOrderRepository.findCustomerOrderById(id);

        // Assert correct response
        assertNotNull(customerOrderFromDb);
        assertEquals(date.toLocalDate(), customerOrderFromDb.getDate().toLocalDate());
        assertEquals(price, customerOrderFromDb.getPrice(), 0.001);
        assertEquals(quantity, customerOrderFromDb.getQuantity());
        assertEquals(offers, customerOrderFromDb.getOffersApplied());
        assertEquals(orderAddress, customerOrderFromDb.getAddress());
        assertEquals(username, customerOrderFromDb.getCustomer().getUsername());
        assertEquals(1, customerOrderFromDb.getPurchased().size());
        assertEquals(videoGame.getId(), customerOrderFromDb.getPurchased().get(0).getId());
    }
}
