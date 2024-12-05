package ca.mcgill.ecse321group1.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import ca.mcgill.ecse321group1.gamestore.service.StaffService;
import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.LoginDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonListDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.model.Staff;


@RestController
@CrossOrigin(origins = "*")
public class StaffController {
    @Autowired
    private StaffService staffService;

    /**
     * Create staff
     * 
     * @param staffToCreate The staff to create.
     * @return The created staff, including their ID.
     */
    @PostMapping("/staff")
    public PersonResponseDto createStaff(@Valid @RequestBody PersonRequestDto staffToCreate) {
        Staff createdStaff = staffService.createStaff(staffToCreate.getUsername(), staffToCreate.getEmail(), staffToCreate.getPassword());
        return new PersonResponseDto(createdStaff);
    }

    /**
     * Get staff by ID
     * 
     * @param sid The primary key (staff ID) of the staff to find.
     * @return The staff with the given ID.
     */
    @GetMapping("/staff/{sid}")
    public PersonResponseDto getStaffById(@PathVariable int sid) {
        Staff gotStaff = staffService.getStaff(sid);
        return new PersonResponseDto(gotStaff);
    }

    /**
     * Retrieves staff by ID and allows attributes to be edited.
     * 
     * @param sid The primary key (staff ID) of the staff to edit.
     * @param request The person request DTO with new username, email, and password.
     * @return The staff with changed attributes.
     */
    @PutMapping("/staff/{sid}")
    public PersonResponseDto editStaffById(@PathVariable int sid, @Valid @RequestBody PersonRequestDto request) {
        Staff editedStaff = staffService.editStaff(sid, request.getUsername(), request.getEmail(), request.getPassword());
        return new PersonResponseDto(editedStaff);
    }

    /**
     * Delete staff by ID
     * 
     * @param pid The primary key (staff ID) of the staff to delete.
     */
    @DeleteMapping("/staff/{pid}")
    public void deleteStaffById(@PathVariable int pid) {
        staffService.deleteStaff(pid);
    }

    /**
     * Verify staff login
     * 
     * @param LoginDto
     * @return null if invalid credentials, Staff object if valid.
     */
    @PostMapping("/login/staff")
    public PersonResponseDto verifyStaff(@Valid @RequestBody LoginDto request) {
        Staff staff = staffService.getByPasswordUsername(request.getUsername(), request.getPassword());

        if (staff == null) {
            return null;
        } else {
            return new PersonResponseDto(staff);
        }
    }

    /**
     * Get all staff
     * 
     * @return A list of all staff as a personListDto
     */
    @GetMapping("/staff/all")
    public PersonListDto getAllStaff() {
        List<Staff> staffRaw = staffService.getAllStaff();
        List<PersonResponseDto> dtoList = new ArrayList<>();

        for (Staff staff : staffRaw) {
            dtoList.add(new PersonResponseDto(staff));
        }

        return new PersonListDto(dtoList);
    }
}   
