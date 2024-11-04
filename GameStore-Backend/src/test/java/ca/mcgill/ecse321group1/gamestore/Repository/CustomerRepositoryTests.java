package ca.mcgill.ecse321group1.gamestore.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;

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

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPasswordHash(passwordHash);
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);
        customer = customerRepository.save(customer);

        // Read Customer from database
        int id = customer.getId();
        Customer customerFromDb = customerRepository.findCustomerById(id);

        // Assert correct responses
        assertNotNull(customerFromDb);
        assertEquals(username, customerFromDb.getUsername());
        assertEquals(email, customerFromDb.getEmail());
        assertEquals(passwordHash, customerFromDb.getPasswordHash());
        assertEquals(address, customerFromDb.getAddress());
        assertEquals(phoneNumber, customerFromDb.getPhoneNumber());
    }
}
