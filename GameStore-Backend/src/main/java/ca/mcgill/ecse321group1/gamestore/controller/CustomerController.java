package ca.mcgill.ecse321group1.gamestore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import ca.mcgill.ecse321group1.gamestore.service.CustomerService;
import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.LoginDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.model.Customer;

@RestController
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Create customer WORKING
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
     * Get customer by ID WORKING
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
     * Retrieves customer by ID and allows attributes to be edited. WORKING
     * 
     * @param cid The primary key (customer ID) of the cGameStore-Backend/src/test/java/ca/mcgill/ecse321group1/gamestore/integration/CustomerIntegrationTests.javaustomer to edit.
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
     * Add quantity number of game to customer cart WORKING
     * 
     * @param cid Customer ID of the customer you want to add the game to.
     * @param gid Game ID of the game you want to add to the customer's cart.
     * @param q Quantity of the game you want to add to the customer's cart.
     * @return The customer with the updated cart
     */
    @PostMapping("/customer/{cid}/cart/{gid}/quantity/{q}")
    public PersonResponseDto addGameToCustomerCartById(@PathVariable int cid, @PathVariable int gid, @PathVariable int q) {
        return new PersonResponseDto(customerService.addToCart(cid, gid, q));
    }

    /**
     * Add game to customer wishlist (game must not be already added) WORKING
     * 
     * @param cid Customer ID of the customer you want to add the game to.
     * @param gid The Video Game ID of the game you want to add to the customer's wishlist
     * @return The customer with the updated wishlist
     */
    @PutMapping("/customer/{cid}/wishlist/{gid}")
    public PersonResponseDto addGameToCustomerWishlist(@PathVariable int cid, @PathVariable int gid) {
        return new PersonResponseDto(customerService.addToWishlist(cid, gid));
    }

    /**
     * Remove all copies of specific game from cart WORKING
     * 
     * @param cid Customer CID of the customer whose cart we want to remove the game from.
     * @param gid The Video Game ID of the game you want to remove from the customer's cart.
     */
    @DeleteMapping("/customer/{cid}/cart/{gid}")
    public void deleteGameFromCart(@PathVariable int cid, @PathVariable int gid) {
        customerService.removeFromCart(cid, gid);
    }

    /**
     * Remove game from wishlist
     * 
     * @param cid Customer CID of the customer whose wishlist we want to remove the game from.
     * @param gid The video game ID of the game you want to remove from the customer's wishlist.
     */
    @DeleteMapping("/customer/{cid}/wishlist/{gid}")
    public void deleteGameFromWishlist(@PathVariable int cid, @PathVariable int gid) {
        customerService.removeFromWishlist(cid, gid);
    }

    /**
     * Return past orders of customer 
     * 
     * @param cid Customer ID of the customer whom we want to get past orders of.
     */
    @GetMapping("/customer/{cid}/pastorders")
    public String getPastOrders(@PathVariable int cid) {
        return customerService.getPastOrdersString(cid);
    }

    /**
     * Clear customer's cart
     * 
     * @param cid Customer ID of the customer whose cart we want to clear
     */
    @DeleteMapping("/customer/{cid}/cart")
    public void clearCart(@PathVariable int cid) {
        customerService.removeAllFromCart(cid);
    }

    /**
     * Verify customer login
     * 
     * @param LoginDto 
     * @return null if invalid credentials, Customer object if valid
     */
    @PostMapping("/login/customer")
    public PersonResponseDto verifyCustomer(@Valid @RequestBody LoginDto request) {
        Customer cust = customerService.getByPasswordUsername(request.getUsername(), request.getPassword());

        if (cust == null) {
            return null;
        } else {
            return new PersonResponseDto(cust);
        }
    }
}
