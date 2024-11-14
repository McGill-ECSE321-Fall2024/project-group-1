package ca.mcgill.ecse321group1.gamestore.dto;

public class PersonResponseDto {
    private int id;
    private String name;
    private String username;
    private String email;
    private String passwordHash;
    private String address;
    private String phoneNumber;

    public PersonResponseDto(int id, String name, String username, String email, String passwordHash) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // For customers
    public PersonResponseDto(int id, String name, String username, String email, String passwordHash, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
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