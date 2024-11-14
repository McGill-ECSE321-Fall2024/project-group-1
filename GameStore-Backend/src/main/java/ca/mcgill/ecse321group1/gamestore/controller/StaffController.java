package ca.mcgill.ecse321group1.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import ca.mcgill.ecse321group1.gamestore.service.StaffService;
import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.model.Staff;


@RestController
public class StaffController {
    @Autowired
    private StaffService staffService;

    /**
     * Create staff
     * 
     */
    @PostMapping("/staff")
    public PersonResponseDto createStaff(@Valid @RequestBody PersonRequestDto personToCreate) {
        Staff createdPerson = staffService.createStaff(personToCreate.getUsername(), personToCreate.getEmail(), personToCreate.getPasswordHash());
        return new PersonResponseDto(createdPerson);
    }
}
