package ca.mcgill.ecse321group1.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.Person;
import ca.mcgill.ecse321group1.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private OwnerRepository ownerRepo;
    @Autowired
    private StaffRepository staffRepo;

    /**Retrieves a Customer object based on lookup by id*/
    @Transactional
    public Customer getCustomer(int cid) {
        Customer c = customerRepo.findCustomerById(cid);
        if (c == null) {
            throw new IllegalArgumentException("There is no customer with ID " + cid + ".");
        }
        return c;
    }

    /**Creates a new Customer object with given attributes, and stores it into the databases*/
    @Transactional
    public Customer createCustomer(String username, String email, String password, String address, String phonenumber) {
        //returns hashed password if it doesn't throw an exception.
        String hashed = PersonServiceHelper.attemptCreate(username, email, password, customerRepo, staffRepo, ownerRepo);

        Customer custToCreate = new Customer();
        custToCreate.setUsername(username);
        custToCreate.setEmail(email);
        custToCreate.setAddress(address);
        custToCreate.setPhoneNumber(phonenumber);
        custToCreate.setPasswordHash(hashed);
        return customerRepo.save(custToCreate);//no ID, gets set by this.
    }

    /**Retrieves a Customer by ID, and modifies its attributes according to editCustomer's arguments before re-storing*/
    @Transactional
    public Customer editCustomer(int id, String username, String email, String password, String address, String phonenumber) {
        Customer customer = customerRepo.findById(id).orElse(null);

        if (customer == null)
            throw new IllegalArgumentException(id + " does not correspond to an extant Customer!");

        String hashed = PersonServiceHelper.attemptCreate(username, email, password, customerRepo, staffRepo, ownerRepo);//throws exceptions if invalid, otherwise returns password hash

        customer.setUsername(username);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setPhoneNumber(phonenumber);
        customer.setPasswordHash(hashed);
        return customerRepo.save(customer);//already has an ID;
    }
    /**Deletes a Customer, permanently.*/
    @Transactional
    public void deleteCustomer(int id) {
        if (!customerRepo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant Customer!");
        customerRepo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }
}