package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.CustomerOrder;

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
        // Create customer
        String username = "JaneDoe";
        String email = "janedoe@example.com";
        String passwordHash = "securepassword";
        String address = "456 Market St";
        String phoneNumber = "0987654321";
        Customer customer = new Customer(username, email, passwordHash, address, phoneNumber);
        customer = customerRepository.save(customer);

        // Create customer order
        String date = "2024-10-12";
        String price = "59.99";
        String quantity = "1";
        String offersApplied = "None";
        String orderAddress = "456 Market St";
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setDate(date);
        customerOrder.setPrice(price);
        customerOrder.setQuantity(quantity);
        customerOrder.setOffersApplied(offersApplied);
        customerOrder.setAddress(orderAddress);
        customerOrder.setCustomer(customer);

        // Save customer order
        customerOrder = customerOrderRepository.save(customerOrder);
        int savedId = customerOrder.getId();

        // Read customer order from database
        CustomerOrder customerOrderFromDb = customerOrderRepository.findCustomerOrderById(savedId);

        // Assert correct response
        assertNotNull(customerOrderFromDb);
        assertEquals(customerOrderFromDb.getId(), savedId);
        assertEquals(customerOrderFromDb.getDate(), date);
        assertEquals(customerOrderFromDb.getPrice(), price);
        assertEquals(customerOrderFromDb.getQuantity(), quantity);
        assertEquals(customerOrderFromDb.getOffersApplied(), offersApplied);
        assertEquals(customerOrderFromDb.getAddress(), orderAddress);
        assertEquals(customerOrderFromDb.getCustomer().getUsername(), username);
    }
}