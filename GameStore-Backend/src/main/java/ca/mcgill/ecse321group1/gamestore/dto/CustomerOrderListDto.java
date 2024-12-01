package ca.mcgill.ecse321group1.gamestore.dto;

import java.util.List;

public class CustomerOrderListDto {
    private List<CustomerOrderResponseDto> orders;

    public CustomerOrderListDto(List<CustomerOrderResponseDto> orders) {
        this.orders = orders;
    }

    public List<CustomerOrderResponseDto> getOrders() {
        return orders;
    }

    // For Jackson
    protected CustomerOrderListDto() {
        
    }
    
}
