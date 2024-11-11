package ca.mcgill.ecse321group1.gamestore.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import ca.mcgill.ecse321group1.gamestore.dto.VideoGameDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;

@RestController 
public class VideoGameController {
    @Autowired
    private VideoGameService videoGameService;
    
    /**
     * Find video game by ID.
     * 
     * @param pid the primary key of the video game to find. 
     * @return The video game with the given ID.
     */
    @GetMapping("/videogame/{pid}")
    public VideoGameResponseDto findVideoGameById(@PathVariable int pid) {
        VideoGame createdVideoGame = videoGameService.getVideoGame(pid);
        return new VideoGameResponseDto(createdVideoGame);
    }

    /**
     * Create Video Game 
     * 
     * @param name the name of the video game
     * @param description the description of the video game
     * @param price 
     * @param quantity
     * @param date
     * @param category
     * @return The created video game
     */
    //@PostMapping("/videogame") {

    //}
}
