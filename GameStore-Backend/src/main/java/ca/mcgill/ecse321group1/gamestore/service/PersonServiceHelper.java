package ca.mcgill.ecse321group1.gamestore.service;

import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.StaffRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PersonServiceHelper {
    //contains static methods (ie account creation validation, password hashing) shared by all Person children (injecting services into each other did not work properly)

    static String attemptCreate (String username, String email, String password, CustomerRepository customerRepo, StaffRepository staffRepo, OwnerRepository ownerRepo) {
        if (username == null || username.length() < 6) {
            throw new IllegalArgumentException("Usernames must be at least 6 characters long!");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Emails must contain an \"@\" sign!");
        }
        if (password == null || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*"))
            throw new IllegalArgumentException("Passwords must be at least 8 characters long, contain an uppercase and lowercase letter, and at least one number.");

        customerRepo.findAll().iterator().forEachRemaining(a -> {
            if (a.getUsername().equals(username)) throw new IllegalArgumentException("Customer with username \"" + username + "\" already exists!");
        });
        staffRepo.findAll().iterator().forEachRemaining(a -> {
            if (a.getUsername().equals(username)) throw new IllegalArgumentException("Staff with username \"" + username + "\" already exists!");
        });
        ownerRepo.findAll().iterator().forEachRemaining(a -> {
            if (a.getUsername().equals(username)) throw new IllegalArgumentException("Owner with username \"" + username + "\" already exists!");
        });
        return hash_password(password);
    }

    static String hash_password(String password) {
        StringBuilder hashed = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest((password + "__^^..rainbow table this").getBytes());

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hashed.append('0');
                hashed.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {}
        return hashed.toString();
    }
}


