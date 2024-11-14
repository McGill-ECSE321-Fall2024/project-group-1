package ca.mcgill.ecse321group1.gamestore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PersonRequestDto {
    @NotBlank(message = "Person name is required.")
    private String name;
    @NotBlank(message = "Person username is required.")
    private String username;
    @Email(message = "Invalid email.")
    private String email;
    @NotBlank(message= "Person password is required.")
    private String passwordHash;
    private String address;
    private String phoneNumber;

    public PersonRequestDto(String name, String username, String email, String passwordHash) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // For customers
    public PersonRequestDto(String name, String username, String email, String passwordHash, String address, String phoneNumber) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    } 
}