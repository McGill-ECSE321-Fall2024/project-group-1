package ca.mcgill.ecse321group1.gamestore.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.Valid;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameListDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.VideoGameResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;
import ca.mcgill.ecse321group1.gamestore.service.CategoryService;


@RestController 
@CrossOrigin(origins = "*")
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
     * @return The video game with matching ID
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

    /**
     * Update video game quantity by a positive or negative number
     * @param vid The primary key (video game ID) of the video game to update quantity on.
     * @param amoount The quantity you want to update the video game by. Can be positive or negative.
     */
    @PostMapping("/videogame/quantity/{vid}/{amount}")
    public VideoGameResponseDto videoGameAlterQuantity(@PathVariable int vid, @PathVariable int amount) {
        VideoGame quantityAlteredVideoGame = videoGameService.alterQuantity(vid, amount);
        return new VideoGameResponseDto(quantityAlteredVideoGame);
    }

    /**
     * Get all pending video games
     * @return List of video games with pending status.
     */
    @GetMapping("/videogame/pending")
    public VideoGameListDto allPendingVideoGames() {
        List<VideoGame> pendingVideoGameList = videoGameService.getAllPending();
        List<VideoGameResponseDto> dtoList = new ArrayList<>();

        for (VideoGame videoGame : pendingVideoGameList) {
            dtoList.add(new VideoGameResponseDto(videoGame)); // Convert and add to the new list
        }
        
        return new VideoGameListDto(dtoList);
    }

    /**
     * Get all video games that match keyword
     * @param keyword The keyword that you want to match
     * @return List of video games that match the keyword.
     */
    @GetMapping("/videogame/search/{keyword}")
    public VideoGameListDto allKeywordMatchingVideoGames(@PathVariable String keyword) {
        List<VideoGame> matchingVideoGameList = videoGameService.searchByKeyword(keyword);
        List<VideoGameResponseDto> dtoList = new ArrayList<>();

        for (VideoGame videoGame : matchingVideoGameList) {
            dtoList.add(new VideoGameResponseDto(videoGame)); // Convert and add to the new list
        }

        return new VideoGameListDto(dtoList);
    }

    /**
     * Get all video games in a given category
     * @param cid The ID of the category that you want to return all games of. 
     * @return List of video games in the given category.
     */
    @GetMapping("/videogame/getcategory/{cid}")
    public VideoGameListDto videoGamesInCategory(@PathVariable int cid) {
        List<VideoGame> gamesInCategory = videoGameService.searchByCategory(cid);
        List<VideoGameResponseDto> dtoList = new ArrayList<>();

        for (VideoGame videoGame : gamesInCategory) {
            dtoList.add(new VideoGameResponseDto(videoGame));
        }

        return new VideoGameListDto(dtoList);
    }

    /**
     * Delete video game with matching ID
     * @param vid The ID of the video game you want to delete
     */
    @DeleteMapping("/videogame/{vid}")
    public void deleteVideoGameById(@PathVariable int vid) {
        videoGameService.deleteVideoGame(vid);
    }

    /**
     * Get average rating of video game
     * @param vid The ID of the video game you want to get the average rating from
     * @return The average rating as a Review.Rating enum.
     */
    @GetMapping("/videogame/{vid}/rating") 
    public Review.Rating getVideoGameRating(@PathVariable int vid) {
        return videoGameService.averageRatingOf(vid);
    }

    /**
     * Get all video games
     * 
     * @return The list of all video games as a VideoGameListDto
     */
    @GetMapping("/videogame")
    public VideoGameListDto getAllVideoGames() {
        List<VideoGame> videoGames = videoGameService.getAll();
        List<VideoGameResponseDto> dtoList = new ArrayList<>();

        for (VideoGame videoGame : videoGames) {
            dtoList.add(new VideoGameResponseDto(videoGame));
        }

        return new VideoGameListDto(dtoList);
    }
}
