package ca.mcgill.ecse321group1.gamestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import ca.mcgill.ecse321group1.gamestore.service.CustomerService;
import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.model.Customer;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Create customer
     * 
     * @param customerToCreate The customer to create.
     * @return The created customer, including their ID.
     */
    @PostMapping("/customer")
    public PersonResponseDto createCustomer(@Valid @RequestBody PersonRequestDto customerToCreate) {
        Customer createdCustomer = customerService.createCustomer(customerToCreate.getUsername(), customerToCreate.getEmail(), customerToCreate.getPassword(), customerToCreate.getAddress(), customerToCreate.getPhoneNumber());
        return new PersonResponseDto(createdCustomer);
    }

    /**
     * Get customer by ID
     * 
     * @param cid The primary key (customer ID) of the customer to get.
     * @return The customer with the given ID.
     */
    @GetMapping("/customer/{cid}")
    public PersonResponseDto getCustomerById(@PathVariable int cid) {
        Customer gotCustomer = customerService.getCustomer(cid);
        return new PersonResponseDto(gotCustomer);
    }

    /**
     * Retrieves customer by ID and allows attributes to be edited.
     * 
     * @param cid The primary key (customer ID) of the customer to edit.
     * @param request The person resquest DTO with new username, email, password, address, and phone number.
     * @return The customer with changed attributes.
     */
     @PutMapping("/customer/{cid}")
     public PersonResponseDto editCustomerById(@PathVariable int cid, @Valid @RequestBody PersonRequestDto request) {
        Customer editedCustomer = customerService.editCustomer(cid, request.getUsername(), request.getEmail(), request.getPassword(), request.getAddress(), request.getPhoneNumber());
        return new PersonResponseDto(editedCustomer);
    }

    /**
     * Delete customer by ID
     * 
     * @param cid The primary key (customer ID) of the staff to delete.
     */
    @DeleteMapping("/customer/{cid}")
    public void deleteCustomerById(@PathVariable int cid) {
        customerService.deleteCustomer(cid);
    }

    /**
     * Add game to customer cart
     * 
     * @param cid Customer ID of the customer you want to add the game to.
     * @param gid Game ID of the game you want to add to the customer's cart.
     * @param q Quantity of the game you want to add to the customer's cart.
     */
    @PostMapping("/customer/{cid}/{gid}/{q}")
    public PersonResponseDto addGameToCustomerCartById(@PathVariable int cid, @PathVariable int gid, @PathVariable int q) {
        return new PersonResponseDto(customerService.addToCart(cid, gid, q));
    }
}
