package ca.mcgill.ecse321group1.gamestore.service;

import ca.mcgill.ecse321group1.gamestore.model.Owner;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.StaffRepository;
import jakarta.transaction.Transactional;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private OwnerRepository ownerRepo;
    @Autowired
    private StaffRepository staffRepo;

    /**Retrieves a Owner object based on lookup by id*/
    @Transactional
    public Owner getOwner(int cid) {
        Owner c = ownerRepo.findOwnerById(cid);
        if (c == null) {
            throw new IllegalArgumentException("There is no owner with ID " + cid + ".");
        }
        return c;
    }

    /**Creates a new Owner object with given attributes, and stores it into the databases*/
    @Transactional
    public Owner createOwner(String username, String email, String password) {
        //returns hashed password if it doesn't throw an exception.
        String hashed = PersonServiceHelper.attemptCreate(username, email, password, customerRepo, staffRepo, ownerRepo);

        Owner ownerTC = new Owner();
        ownerTC.setUsername(username);
        ownerTC.setEmail(email);
        ownerTC.setPasswordHash(hashed);
        return ownerRepo.save(ownerTC);//no ID, gets set by this.
    }

    /**Retrieves a Owner by ID, and modifies its attributes according to editOwner's arguments before re-storing*/
    @Transactional
    public Owner editOwner(int id, String username, String email, String password) {
        Owner owner = ownerRepo.findById(id).orElse(null);

        if (owner == null)
            throw new IllegalArgumentException(id + " does not correspond to an extant Owner!");

        String hashed = PersonServiceHelper.attemptCreate(username, email, password, customerRepo, staffRepo, ownerRepo);//throws exceptions if invalid, otherwise returns password hash

        owner.setUsername(username);
        owner.setEmail(email);
        owner.setPasswordHash(hashed);
        return ownerRepo.save(owner);//already has an ID;
    }
    /**Deletes an Owner, permanently.*/
    @Transactional
    public void deleteOwner(int id) {
        if (!ownerRepo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant Owner!");
        if (ownerRepo.count() < 2)
            throw new IllegalArgumentException("You cannot remove the last Owner.");
        ownerRepo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }

    /*Returns null if no Owner with identical username and password.*/
    public Owner getByPasswordUsername (String username, String password) {
        String hash = PersonServiceHelper.hash_password(password);
        AtomicReference<Owner> C = new AtomicReference<>();
        ownerRepo.findAll().forEach(c -> {
            if (c.getUsername().equals(username) && c.getPasswordHash().equals(hash)) C.set(c);
        });
        return C.get();
    }
}