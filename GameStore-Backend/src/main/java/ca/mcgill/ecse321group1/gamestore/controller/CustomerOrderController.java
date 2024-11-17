package ca.mcgill.ecse321group1.gamestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321group1.gamestore.dto.CustomerOrderRequestDto;
import ca.mcgill.ecse321group1.gamestore.model.CustomerOrder;
import ca.mcgill.ecse321group1.gamestore.service.CustomerOrderService;
import ca.mcgill.ecse321group1.gamestore.service.OfferService;
import jakarta.validation.Valid;

@RestController
public class CustomerOrderController {
    @Autowired
    private CustomerOrderService orderService;
    @Autowired
    private OfferService offerService;

    /**
     * Get customer objects by shared ID
     * @param sid Shared ID
     * @return The list of CustomerObjects
     */
    @GetMapping("/order/{sid}")
    public List<CustomerOrder> getCustomerOrder(@PathVariable int sid) {
        return orderService.getCustomerOrders(sid);
    }

    /**
     * Creates customer orders from a nonempty cart and returns shared id.
     * @param Customer The customer
     * @param Date The date of the order
     * @param address The address to ship to
     * @param vidservice The video game service
     * @return The list of customer orders
     */
    @PostMapping("/order")
    public List<CustomerOrder> generateOrders(@Valid @RequestBody CustomerOrderRequestDto orderToGenerate) {
        return orderService.generateOrdersFromCustomerCart(orderToGenerate.getCustomer(), orderToGenerate.getDate(), orderToGenerate.getAddress(), orderToGenerate.getService());
    }

    /**
     * Delete set of CustomerOrders by shared id
     * @param sid The shared id of the customer orders that you want to delete
     */
    @DeleteMapping("/order/{sid}")
    public void deleteOrders(@PathVariable int sid) {
        orderService.deleteCustomerOrders(sid);
    }

    /**
     * Calculate price of set of customer orders
     * @param sid The shared ID of customer orders
     * @return Float of the total price
     */
    @GetMapping("/order/{sid}/price")
    public Float getOrderPrice(@PathVariable int sid) {
        return orderService.getTotalPrice(sid);
    }
    

    /**
     * Get all unsatisfied orders for a particular game
     * @param vid The video game ID
     * @return The list of customer orders
     */
    @GetMapping("/order/game/{vid}") 
    public List<CustomerOrder> getUnsatisfied(@PathVariable int vid) {
        return orderService.getAllUnsatisfied(vid);
    }

    /**
     * Make list of orders satisfied or unsatisfied
     * @param satisfied Boolean. True sets orders all to satisfied, vice versa for false
     * @param orders List of customer orders we want to change satisfied.
     */
}
