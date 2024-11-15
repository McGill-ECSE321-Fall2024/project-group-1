package ca.mcgill.ecse321group1.gamestore.controller;

import java.sql.Date;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.Valid;

import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;
import ca.mcgill.ecse321group1.gamestore.service.CategoryService;


@RestController 
public class VideoGameController {
    @Autowired
    private VideoGameService videoGameService;
    @Autowired
    private CategoryService categoryService;

    /**
     * Create video game
     * 
     * @param videoGameToCreate The video game to create.
     * @return The created video game, including the ID.
     */
    @PostMapping("/videogame")
    @ResponseStatus(HttpStatus.OK)
    public VideoGameResponseDto createVideoGame(@Valid @RequestBody VideoGameRequestDto videoGameToCreate) {
        int categoryId = videoGameToCreate.getCategoryId();
        Category category = categoryService.getCategory(categoryId);
        VideoGame createdVideoGame = videoGameService.createVideoGame(videoGameToCreate.getName(), videoGameToCreate.getDescription(), videoGameToCreate.getPrice(), videoGameToCreate.getQuantity(), Date.valueOf(videoGameToCreate.getDate()), category);
        return new VideoGameResponseDto(createdVideoGame);
    }
    
    /**
     * Get video game by ID
     * 
     * @param vid The primary key (video game ID) of the video
     */
}
