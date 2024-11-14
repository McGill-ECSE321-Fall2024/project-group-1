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

import ca.mcgill.ecse321group1.gamestore.dto.PersonResponseDto;
import ca.mcgill.ecse321group1.gamestore.dto.PersonRequestDto;
import ca.mcgill.ecse321group1.gamestore.service.OwnerService;


@RestController
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    /**
     * Create owner
     * 
     * @param Owner The owner to create
     * @return The created owner
     */
    @PostMapping
    public PersonResponseDto createOwner(@Valid @RequestBody PersonRequestDto ownerToCreate) {
        return null;
    }
}
