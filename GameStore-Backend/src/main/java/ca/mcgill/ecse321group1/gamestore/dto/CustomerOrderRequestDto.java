package ca.mcgill.ecse321group1.gamestore.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.CustomerOrder;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;

public class CustomerOrderRequestDto {
    private Customer customer;
    private Date date;
    private String address;
    private VideoGameService service;
    private List<CustomerOrder> orders;

    public CustomerOrderRequestDto(Customer customer, Date date, String address, VideoGameService service) {
        this.customer = customer;
        this.date = date;
        this.address = address;
        this.service = service;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public VideoGameService getService() {
        return service;
    }
    
    public CustomerOrderRequestDto(List<CustomerOrder> orders) {
        this.orders = orders;
    }


    
}
