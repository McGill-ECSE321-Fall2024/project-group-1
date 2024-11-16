package ca.mcgill.ecse321group1.gamestore.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321group1.gamestore.dto.OfferRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.OfferResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Offer;
import ca.mcgill.ecse321group1.gamestore.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;

@RestController
public class OfferController {
    @Autowired
    private OfferService offerService;
    @Autowired
    private VideoGameService videoGameService;
    
    /**
     * Create new offer
     * 
     * @param offerToCreate The offer to create.
     * @return The created offer, including the ID.
     * 
     */
    @PostMapping("/offer")
    public OfferResponseDto createOffer(@Valid @RequestBody OfferRequestDto offerToCreate) {
        VideoGame gotVideoGame = videoGameService.getVideoGame(offerToCreate.getVideoGameId());
        Offer createdOffer = offerService.createOffer(offerToCreate.getName(), offerToCreate.getDescription(), offerToCreate.getEffect(), Date.valueOf(offerToCreate.getStartDate()), Date.valueOf(offerToCreate.getEndDate()), gotVideoGame);
                
        return new OfferResponseDto(createdOffer);
    }

    /**
     * Get offer based on ID
     * @param oid The primary key (offer ID) of the offer you want to get.
     * @return The offer with matching ID
     */
    @GetMapping("/offer/{oid}")
    public OfferResponseDto findOfferById(@PathVariable int oid) {
        Offer matchingOffer = offerService.getOffer(oid);
        return new OfferResponseDto(matchingOffer);
    }
}
