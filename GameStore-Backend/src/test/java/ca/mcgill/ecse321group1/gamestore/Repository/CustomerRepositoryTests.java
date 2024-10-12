package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Customer;

@SpringBootTest
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomer() {
        // Create customer
        String username = "JohnDoe";
        String email = "johndoe@example.com";
        String passwordHash = "password123";
        String address = "123 Main St";
        String phoneNumber = "1234567890";

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPasswordHash(passwordHash);
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);

        // Save customer
        customer = customerRepository.save(customer);
        String savedUsername = customer.getUsername();

        // Read customer from database
        Customer customerFromDb = customerRepository.findCustomerByUsername(savedUsername);

        // Assert correct response
        assertNotNull(customerFromDb);
        assertEquals(customerFromDb.getUsername(), username);
        assertEquals(customerFromDb.getEmail(), email);
        assertEquals(customerFromDb.getPasswordHash(), passwordHash);
        assertEquals(customerFromDb.getAddress(), address);
        assertEquals(customerFromDb.getPhoneNumber(), phoneNumber);
    }
}