package ca.mcgill.ecse321group1.gamestore.dto;

import java.util.List;

public class PersonListDto {
    private List<PersonResponseDto> customers;

    public PersonListDto(List<PersonResponseDto> customers) {
        this.customers = customers;
    }

    public List<PersonResponseDto> getCustomers() {
        return customers;
    }

    // For Jackson
    protected PersonListDto() {

    }

}
