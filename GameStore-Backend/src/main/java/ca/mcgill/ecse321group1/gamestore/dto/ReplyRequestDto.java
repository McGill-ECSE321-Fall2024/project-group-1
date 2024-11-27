package ca.mcgill.ecse321group1.gamestore.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReplyRequestDto {
    //@NotBlank(message = "Reply must not be blank!")
    private String content;
   // @NotNull(message = "Reply date must exist!")
    private LocalDate date;

    public ReplyRequestDto(LocalDate date, String content) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getLocalDate() {
        return date;
    }
}
