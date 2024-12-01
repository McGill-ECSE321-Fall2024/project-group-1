package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321group1.gamestore.model.CustomerOrder;

public class CustomerOrderResponseDto {
    private int id;
    private int sharedId;
    private LocalDate date;
    private float price;
    private int quantity;
    private boolean satisfied;
    private String address;
    private VideoGameResponseDto purchased;

    public CustomerOrderResponseDto(CustomerOrder customerOrder) {
        this.id = customerOrder.getId();
        this.sharedId = customerOrder.getSharedId();
        this.date = customerOrder.getDate().toLocalDate();
        this.price = customerOrder.getPrice();
        this.quantity = customerOrder.getQuantity();
        this.satisfied = customerOrder.getSatisfied();
        this.address = customerOrder.getAddress();
        this.purchased = new VideoGameResponseDto(customerOrder.getPurchased());
    }

    public int getId() {
        return id;
    }

    public int getSharedId() {
        return sharedId;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getSatisfied() {
        return satisfied;
    }

    public String getAddress() {
        return address;
    }

    public VideoGameResponseDto getPurchased() {
        return purchased;
    }

    // For Jackson
    protected CustomerOrderResponseDto() {

    }

}
