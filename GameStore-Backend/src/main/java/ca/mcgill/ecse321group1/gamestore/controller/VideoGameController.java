package ca.mcgill.ecse321group1.gamestore.controller;

import java.sql.Date;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @GetMapping("/videogame/{vid}") 
    public VideoGameResponseDto findVideoGameById(@PathVariable int vid) {
        VideoGame createdVideoGame = videoGameService.getVideoGame(vid);
        return new VideoGameResponseDto(createdVideoGame);
    }

    /**
     * Retrieves video game by ID and allows attributes to be edited.
     * 
     * @param vid The primary key (video game ID) of the video game to edit.
     * @param request The video game request DTO with new attributes.
     * @return The video game with changed attributes
     */
    @PutMapping("/videogame/{vid}")
    public VideoGameResponseDto editVideoGameById(@PathVariable int vid, @Valid @RequestBody VideoGameRequestDto request) {
        VideoGame gotVideoGame = videoGameService.getVideoGame(vid);
        Category gotCategory = categoryService.getCategory(request.getCategoryId());
        VideoGame editedVideoGame = videoGameService.editVideoGame(vid, request.getName(), request.getDescription(), request.getPrice(), Date.valueOf(request.getDate()), gotVideoGame.getStatus(), gotCategory);
        return new VideoGameResponseDto(editedVideoGame);
    }

    /**
     * Move game that is Pending to Active status (approve).
     * @param vid The primary key (video game ID) of the video game to approve.
     * @return The video game that was changed to Active status (approved).
     */
    @PutMapping("/videogame/approve/{vid}")
    public VideoGameResponseDto videoGamePendingToActive(@PathVariable int vid) {
        VideoGame approvedVideoGame = videoGameService.approveGame(vid);
        return new VideoGameResponseDto(approvedVideoGame);
    }

    /**
     * Move game to InActive from any status (deactivate).
     * @param vid The primary key (video game ID) of the video game to change to InActive status. 
     * @return The video game that was changed to InActive status (deactivated).
     */
    @PutMapping("/videogame/deactivate/{vid}")
    public VideoGameResponseDto videoGameToInactive(@PathVariable int vid) {
        VideoGame deactivatedVideoGame = videoGameService.deactivateGame(vid);
        return new VideoGameResponseDto(deactivatedVideoGame);
    }
}
