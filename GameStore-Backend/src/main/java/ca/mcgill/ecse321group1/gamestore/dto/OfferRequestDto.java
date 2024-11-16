package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

public class OfferRequestDto {
    private String name;
    private String description;
    private String effect;
    private LocalDate startDate;
    private LocalDate endDate;
    private int videoGameId;

    public OfferRequestDto(String name, String description, String effect, LocalDate startDate, LocalDate endDate, int videoGameId) {
        this.name = name;
        this.description = description;
        this.effect = effect;
        this.startDate = startDate;
        this.endDate = endDate;
        this.videoGameId = videoGameId;
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
