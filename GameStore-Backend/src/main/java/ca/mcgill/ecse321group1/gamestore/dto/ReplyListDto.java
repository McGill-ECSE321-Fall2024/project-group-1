package ca.mcgill.ecse321group1.gamestore.dto;

import java.util.List;

public class ReplyListDto {
    private List<ReplyResponseDto> replies;

    public List<ReplyResponseDto> getReplies() {
        return replies;
    }

    public ReplyListDto(List<ReplyResponseDto> replies) {
        this.replies = replies;
    }

    // For Jackson
    protected ReplyListDto() {
        
    }
}
