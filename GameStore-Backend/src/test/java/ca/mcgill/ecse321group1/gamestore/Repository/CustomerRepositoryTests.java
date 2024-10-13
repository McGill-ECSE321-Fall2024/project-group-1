package ca.mcgill.ecse321group1.gamestore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Customer;

import java.sql.Date;
import java.util.*;

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
        // Create and Save Customer
        String username = "AlanBrotherton";
        String email = "alanbrotherton@example.com";
        String passwordHash = "password123";
        String address = "1600 Pennsylvania Avenue NW";
        String phoneNumber = "4703237795";
        Customer customer = new Customer(username, email, passwordHash, address, phoneNumber);
        customer = customerRepository.save(customer);

        // Read Customer from database
        int id = customer.getId();
        Customer customerFromDb = customerRepository.findCustomerById(id);

        // Assert correct responses
        assertNotNull(customerFromDb);
        assertEquals(customerFromDb.getUsername(), username);
        assertEquals(customerFromDb.getEmail(), email);
        assertEquals(customerFromDb.getPasswordHash(), passwordHash);
        assertEquals(customerFromDb.getAddress(), address);
        assertEquals(customerFromDb.getPhoneNumber(), phoneNumber);
    }
}