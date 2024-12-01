package ca.mcgill.ecse321group1.gamestore.dto;

public class CustomerOrderCreationDto {
    private int customerId;
    private String address;

    public CustomerOrderCreationDto(int customerId, String address) {
        this.customerId = customerId;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    // For Jackson
    protected CustomerOrderCreationDto() {
        
    }

    

}
