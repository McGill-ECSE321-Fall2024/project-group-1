package ca.mcgill.ecse321group1.gamestore.dto;

import java.util.List;

import ca.mcgill.ecse321group1.gamestore.model.VideoGame;

public class VideoGameListDto {
    private List<VideoGameResponseDto> videoGames;

    public VideoGameListDto(VideoGameResponseDto videoGame) {
        this.videoGames.add(videoGame);
    }

    public VideoGameListDto(List<VideoGameResponseDto> videoGames) {
        this.videoGames = videoGames;
    }

    public List<VideoGameResponseDto> getVideoGames() {
        return videoGames;
    }
    
    // For Jackson
    protected VideoGameListDto() {

    }
    
}
