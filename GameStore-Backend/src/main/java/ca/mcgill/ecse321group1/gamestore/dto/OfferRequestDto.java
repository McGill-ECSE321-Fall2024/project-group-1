package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

public class OfferRequestDto {
    private String name;
    private String description;
    private String effect;
    private LocalDate start;
    private LocalDate end;
    private int videoGameId;

    public OfferRequestDto(String name, String description, String effect, LocalDate start, LocalDate end, int videoGameId) {
        this.name = name;
        this.description = description;
        this.effect = effect;
        this.start = start;
        this.end = end;
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
        return start;
    }

    public LocalDate getEndDate() {
        return end;
    }

    public int getVideoGameId() {
        return videoGameId;
    }
}
