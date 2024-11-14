package ca.mcgill.ecse321group1.gamestore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PersonRequestDto {
    // @NotBlank(message = "Person username is required.")
    private String username;
    // @Email(message = "Invalid email.")
    private String email;
    // @NotBlank(message= "Person password is required.")
    private String password;
    private String address;
    private String phoneNumber;

    // For staff and owners
    public PersonRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    protected PersonRequestDto() {
    }

    // For customers
    public PersonRequestDto(String username, String email, String password, String address, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    } 
}