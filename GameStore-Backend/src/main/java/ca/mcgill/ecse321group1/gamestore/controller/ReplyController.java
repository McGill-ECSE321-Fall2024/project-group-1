package ca.mcgill.ecse321group1.gamestore.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321group1.gamestore.dto.ReplyRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReplyResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Reply;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.service.ReplyService;
import ca.mcgill.ecse321group1.gamestore.service.ReviewService;
import jakarta.validation.Valid;

@RestController
public class ReplyController {
    @Autowired
    private ReplyService replyService;
    @Autowired
    private ReviewService reviewService;

    /**
     * Get reply object by ID
     * @param rid 
     */
    @GetMapping("/review/reply/{rpid}")
    public Reply getReplyById(@PathVariable int rpid) {
        return replyService.getReply(rpid); //  REDO THIS TO FOLLOW POSTMAPPING URI 
    }

    /**
     * Create reply
     * @param rid The ID of the review the reply is to be attached to.
     * @return The created reply 
     */
    @PostMapping("/review/{rid}/reply")
    public ReplyResponseDto createReply(@PathVariable int rid, @Valid @RequestBody ReplyRequestDto request) {
        Review review = reviewService.getReview(rid);
        Reply reply = replyService.createReply(request.getContent(), Date.valueOf(request.getLocalDate()), review);
        String content = reply.getContent();
        Date date = reply.getDate();
        return new ReplyResponseDto(reply);
    }
    
}
