package ca.mcgill.ecse321group1.gamestore.service;

import ca.mcgill.ecse321group1.gamestore.model.Staff;
import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.StaffRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private OwnerRepository ownerRepo;
    @Autowired
    private StaffRepository staffRepo;

    /**Retrieves a Staff object based on lookup by id*/
    @Transactional
    public Staff getStaff(int cid) {
        Staff c = staffRepo.findStaffById(cid);
        if (c == null) {
            throw new IllegalArgumentException("There is no staff with ID " + cid + ".");
        }
        return c;
    }

    /**Creates a new Staff object with given attributes, and stores it into the databases*/
    @Transactional
    public Staff createStaff(String username, String email, String password) {
        //returns hashed password if it doesn't throw an exception.
        String hashed = PersonServiceHelper.attemptCreate(username, email, password, customerRepo, staffRepo, ownerRepo);

        Staff staffTC = new Staff();
        staffTC.setUsername(username);
        staffTC.setEmail(email);
        staffTC.setPasswordHash(hashed);
        return staffRepo.save(staffTC);//no ID, gets set by this.
    }

    /**Retrieves a Staff by ID, and modifies its attributes according to editStaff's arguments before re-storing*/
    @Transactional
    public Staff editStaff(int id, String username, String email, String password) {
        Staff staff = staffRepo.findById(id).orElse(null);

        if (staff == null)
            throw new IllegalArgumentException(id + " does not correspond to an extant Staff!");

        String hashed = PersonServiceHelper.attemptCreate(username, email, password, customerRepo, staffRepo, ownerRepo);//throws exceptions if invalid, otherwise returns password hash

        staff.setUsername(username);
        staff.setEmail(email);
        staff.setPasswordHash(hashed);
        return staffRepo.save(staff);//already has an ID;
    }

    /**Deletes a Staff, permanently.*/
    @Transactional
    public void deleteStaff(int id) {
        if (!staffRepo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant Staff!");
        staffRepo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }

    /*Returns null if no Staff with identical username and password.*/
    public Staff getByPasswordUsername (String username, String password) {
        String hash = PersonServiceHelper.hash_password(password);
        AtomicReference<Staff> C = new AtomicReference<>();
        staffRepo.findAll().forEach(c -> {
            if (c.getUsername().equals(username) && c.getPasswordHash().equals(hash)) C.set(c);
        });
        return C.get();
    }

    /** returns all Staff */
    public ArrayList<Staff> getAllStaff() {
        ArrayList<Staff> a = new ArrayList<>();
        staffRepo.findAll().forEach(a::add);
        return a;
    }
}