package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321group1.gamestore.model.Offer;

public class OfferResponseDto {
    private int id;
    private String name;
    private String description;
    private String effect;
    private LocalDate startDate;
    private LocalDate endDate;
    private int videoGameId;

    protected OfferResponseDto() {    
    }

    public OfferResponseDto(Offer offer) {
        this.id = offer.getId();
        this.name = offer.getName();
        this.description = offer.getDescription();
        this.effect = offer.getEffect();
        this.startDate = offer.getStartDate().toLocalDate();
        this.endDate = offer.getEndDate().toLocalDate();
        this.videoGameId = offer.getVideoGame().getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEffect() {
        return effect;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getVideoGameId() {
        return videoGameId;
    }
    
}
