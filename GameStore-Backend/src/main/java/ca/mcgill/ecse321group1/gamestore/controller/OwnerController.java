package ca.mcgill.ecse321group1.gamestore.controller;

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

import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.LoginDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.service.OwnerService;
import ca.mcgill.ecse321group1.gamestore.model.Owner;



@RestController
@CrossOrigin(origins = "*")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    /**
     * Create owner
     * 
     * @param Owner The owner to create
     * @return The created owner
     */
    @PostMapping("/owner")
    public PersonResponseDto createOwner(@Valid @RequestBody PersonRequestDto ownerToCreate) {
        Owner createdOwner = ownerService.createOwner(ownerToCreate.getUsername(), ownerToCreate.getEmail(), ownerToCreate.getPassword());
        return new PersonResponseDto(createdOwner);
    }

     /**
     * Get owner by ID
     * 
     * @param oid The primary key (owner ID) of the owner to find.
     * @return The owner with the given ID.
     */
    @GetMapping("/owner/{oid}")
    public PersonResponseDto getOwnerById(@PathVariable int oid) {
        Owner gotOwner = ownerService.getOwner(oid);
        return new PersonResponseDto(gotOwner);
    }

    /**
     * Retrieves owner by ID and allows attributes to be edited.
     * 
     * @param oid The primary key (owner ID) of the owner to edit.
     * @param request The person request DTO with new username, email, and password.
     * @return The owner with changed attributes.
     */
    @PutMapping("/owner/{oid}")
    public PersonResponseDto editOwnerById(@PathVariable int oid, @Valid @RequestBody PersonRequestDto request) {
        Owner editedOwner = ownerService.editOwner(oid, request.getUsername(), request.getEmail(), request.getPassword());
        return new PersonResponseDto(editedOwner);
    }

    /**
     * Delete owner by ID
     * 
     * @param oid The primary key (owner ID) of the owner to delete.
     */
    @DeleteMapping("/owner/{oid}")
    public void deleteOwnerById(@PathVariable int oid) {
        ownerService.deleteOwner(oid);
    }

    /**
     * Verify owner login
     * 
     * @param LoginDto
     * @return null if invalid credentials, Owner object if valid.
     */
    @PostMapping("/login/owner")
    public PersonResponseDto verifyOwner(@Valid @RequestBody LoginDto request) {
        Owner owner = ownerService.getByPasswordUsername(request.getUsername(), request.getPassword());

        if (owner == null) {
            return null;
        } else {
            return new PersonResponseDto(owner);
        }
    }
}
