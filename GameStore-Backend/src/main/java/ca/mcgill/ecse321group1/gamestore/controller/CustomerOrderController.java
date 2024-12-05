package ca.mcgill.ecse321group1.gamestore.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321group1.gamestore.dto.CustomerOrderResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.CustomerOrder;
import ca.mcgill.ecse321group1.gamestore.service.CustomerOrderService;
import ca.mcgill.ecse321group1.gamestore.service.CustomerService;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;
import ca.mcgill.ecse321group1.gamestore.dto.CustomerOrderCreationDto;
import ca.mcgill.ecse321group1.gamestore.dto.CustomerOrderListDto;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class CustomerOrderController {
    @Autowired
    private CustomerOrderService customerOrderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VideoGameService videoGameService;

    /**
     * Creates customer orders from a nonempty cart and returns shared id. 
     * @param cid The customer ID whose cart we want to create an order from
     * @param request The CustomerOrderCreationDto object with order attributes.
     * @return A customerOrderListDto of all customer orders in the order as CustomerOrderResponseDtos 
     */
    @PostMapping("/customer/{cid}/order")
    public CustomerOrderListDto generateOrders(@PathVariable int cid, @Valid @RequestBody CustomerOrderCreationDto request) {
        Customer customer = customerService.getCustomer(cid);
        Date curDate = new Date(System.currentTimeMillis());

        List<CustomerOrder> customerOrders = customerOrderService.generateOrdersFromCustomerCart(customer, curDate, request.getAddress(), videoGameService);
        List<CustomerOrderResponseDto> dtoList = new ArrayList<>();
        
        for (CustomerOrder customerOrder : customerOrders) {
            dtoList.add(new CustomerOrderResponseDto(customerOrder));
        }

        return new CustomerOrderListDto(dtoList);
    }

    /**
     * Get customer order objects by shared ID 
     * @param sid Shared ID of the customer orders
     * @return The list of Customer Order objects as a CustomerOrderListDto
     */
    @GetMapping("/order/{sid}")
    public CustomerOrderListDto getCustomerOrderBySharedId(@PathVariable int sid) {
        List<CustomerOrder> gotOrders = customerOrderService.getCustomerOrders(sid);
        List<CustomerOrderResponseDto> dtoList = new ArrayList<>();
        
        for (CustomerOrder customerOrder : gotOrders) {
            dtoList.add(new CustomerOrderResponseDto(customerOrder));
        }

        return new CustomerOrderListDto(dtoList);
    }


    /**
     * Delete set of CustomerOrders by shared id
     * @param sid The shared id of the customer orders that you want to delete
     */
    @DeleteMapping("/order/{sid}")
    public void deleteOrders(@PathVariable int sid) {
        customerOrderService.deleteCustomerOrders(sid);
    }

    /**
     * Calculate price of set of customer orders 
     * @param sid The shared ID of customer orders
     * @return Float of the total price
     */
    @GetMapping("/order/{sid}/price")
    public Float getOrderPrice(@PathVariable int sid) {
        return customerOrderService.getTotalPrice(sid);
    }
    

    /**
     * Get all unsatisfied orders for a particular game 
     * @param vid The video game ID
     * @return The list of customer orders of the particular game that are unsatisfied as a CustomerOrderListDto
     */
    @GetMapping("/videogame/{vid}/unsatisfied") 
    public CustomerOrderListDto getUnsatisfied(@PathVariable int vid) {
        List<CustomerOrder> orders = customerOrderService.getAllUnsatisfied(vid);
        List<CustomerOrderResponseDto> dtoList = new ArrayList<>();

        for (CustomerOrder order : orders) {
            dtoList.add(new CustomerOrderResponseDto(order));
        }

        return new CustomerOrderListDto(dtoList);
    }

    /**
     * Make list of orders satisfied or unsatisfied 
     * @param sat Boolean. True sets orders passed in all to satisfied, vice versa for false.
     * @param orders List of customer orders we want to change satisfied parameter as a CustomerOrderListDto
     */
    @PutMapping("/order/update")
    public void setSatisfied(@Valid @RequestBody CustomerOrderListDto orders) {
        List<CustomerOrderResponseDto> ordersDtoList = orders.getOrders();
        List<CustomerOrder> orderList = new ArrayList<>();

        for (CustomerOrderResponseDto custOrder : ordersDtoList) {
            orderList.add(customerOrderService.getCustomerOrder(custOrder.getId()));
        }

        customerOrderService.setSatisfied(true, orderList);
    }

    /**
     * Get all customer orders
     * @param cid The customer ID of the customer whom you want to get all orders for.
     * @return All customer order of a customer 
     */
    @GetMapping("/order/customer/{cid}")
    public CustomerOrderListDto getSpecificCustomerOrder(@PathVariable int cid) {
        List<CustomerOrder> orders = customerOrderService.getAllOrders(cid);
        List<CustomerOrderResponseDto> dtoList = new ArrayList<>();

        for (CustomerOrder order : orders) {
            dtoList.add(new CustomerOrderResponseDto(order));
        }

        return new CustomerOrderListDto(dtoList);
    }
}
