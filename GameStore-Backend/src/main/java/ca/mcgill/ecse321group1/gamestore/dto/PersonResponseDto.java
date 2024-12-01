package ca.mcgill.ecse321group1.gamestore.dto;

import ca.mcgill.ecse321group1.gamestore.model.Staff;
import ca.mcgill.ecse321group1.gamestore.model.Owner;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321group1.gamestore.model.Customer;

public class PersonResponseDto {
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private String address;
    private String phoneNumber;
    private List<VideoGameResponseDto> wishlist;
    private List<VideoGameResponseDto> cart;

    protected PersonResponseDto() {
    }

    public PersonResponseDto(Staff staff) {
        this.id = staff.getId();
        this.username = staff.getUsername();
        this.email = staff.getEmail();
        this.passwordHash = staff.getPasswordHash();
    }

    public PersonResponseDto(Owner owner) {
        this.id = owner.getId();
        this.username = owner.getUsername();
        this.email = owner.getEmail();
        this.passwordHash = owner.getPasswordHash();
    }

    public PersonResponseDto(Customer customer) {
        this.id = customer.getId();
        this.username = customer.getUsername();
        this.email = customer.getEmail();
        this.passwordHash = customer.getPasswordHash();
        this.address = customer.getAddress();
        this.phoneNumber = customer.getPhoneNumber();

        List<VideoGame> wishlistRaw = customer.getWishlist();
        List<VideoGameResponseDto> wishlistDto = new ArrayList<>();
        for (VideoGame wlVideoGame : wishlistRaw) {
            wishlistDto.add(new VideoGameResponseDto(wlVideoGame));
        }
        this.wishlist = wishlistDto; 

        List<VideoGame> cartRaw = customer.getCart();
        List<VideoGameResponseDto> cartDto = new ArrayList<>();
        for (VideoGame cartVideoGame : cartRaw) {
            cartDto.add(new VideoGameResponseDto(cartVideoGame));
        }
        this.cart = cartDto;
    }

    public int getId() {
        return id;
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

    public List<VideoGameResponseDto> getCart() {
        return cart;
    }

    public List<VideoGameResponseDto> getWishlist() {
        return wishlist;
    }
}