package ca.mcgill.ecse321group1.gamestore.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import ca.mcgill.ecse321group1.gamestore.dto.VideoGameDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;

@RestController 
public class VideoGameController {
    @Autowired
    private VideoGameService videoGameService;

    /**
     * Create video game
     * @param videoGameToCreate The video game to create.
     * @return The created video game, including the generated video game ID.
     */
    @PostMapping("/videogame")
    public VideoGameResponseDto createVideoGame(@Valid @RequestBody VideoGameRequestDto videoGameToCreate) {
        VideoGame createdVideoGame = videoGameService.createVideoGame(
            videoGameToCreate.getName(),
            videoGameToCreate.getDescription(),
            videoGameToCreate.getPrice(),
            videoGameToCreate.getQuantity(),
            videoGameToCreate.getDate(),
            videoGameToCreate.getCategory()
        );
        return new VideoGameResponseDto(createdVideoGame); 
    }
    
    /**
     * Find video game by ID.
     * 
     * @param vid the id of the video game to find. 
     * @return The video game with the given ID.
     */
    @GetMapping("/videogame/{vid}") // DOUBLE CHECK IF THIS WORKS
    public VideoGameResponseDto findVideoGameById(@PathVariable int vid) {
        VideoGame createdVideoGame = videoGameService.getVideoGame(vid);
        return new VideoGameResponseDto(createdVideoGame);
    }
}
