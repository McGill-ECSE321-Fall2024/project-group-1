package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.CustomerOrder;

import java.sql.Date;
import java.util.*;

@SpringBootTest
public class CustomerOrderRepositoryTests {
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void clearDatabase() {
        customerOrderRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomerOrder() {
        // Create and Save Customer
        String username = "AlanBrotherton";
        String email = "alanbrotherton@example.com";
        String passwordHash = "password123";
        String address = "1600 Pennsylvania Avenue NW";
        String phoneNumber = "4703237795";
        Customer customer = new Customer(username, email, passwordHash, address, phoneNumber);
        customer = customerRepository.save(customer);

        // Create and Save Customer Order
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        Date date = new java.sql.Date(calendar.getTimeInMillis());
        Float price = 79.99f;
        Integer quanity = 10;
        String offers = "20% Off!";
        String orderAddress = "7700 Bd Decarie";
        CustomerOrder customerOrder = new CustomerOrder(date, price, quanity, offers, orderAddress, customer);
        customerOrder = customerOrderRepository.save(customerOrder);

        // Read Customer Order from database
        int id = customerOrder.getId();
        CustomerOrder customerOrderFromDb = customerOrderRepository.findCustomerOrderById(id);

        // Assert correct response
        assertNotNull(customerOrderFromDb);
        assertEquals(customerOrderFromDb.getDate().toLocalDate(), date.toLocalDate());
        assertEquals(customerOrderFromDb.getPrice(), price);
        assertEquals(customerOrderFromDb.getQuantity(), quanity);
        assertEquals(customerOrderFromDb.getOffersApplied(), offers);
        assertEquals(customerOrderFromDb.getAddress(), orderAddress);
        assertEquals(customerOrderFromDb.getCustomer().getUsername(), username);
    }
}