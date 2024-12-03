package ca.mcgill.ecse321group1.gamestore.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import ca.mcgill.ecse321group1.gamestore.dto.ReplyListDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReplyRequestDto;
import ca.mcgill.ecse321group1.gamestore.dto.ReplyResponseDto;
import ca.mcgill.ecse321group1.gamestore.model.Reply;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.service.ReplyService;
import ca.mcgill.ecse321group1.gamestore.service.ReviewService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class ReplyController {
    @Autowired
    private ReplyService replyService;
    @Autowired
    private ReviewService reviewService;

    /**
     * Get reply object by ID
     * @param rpid The ID of the reply to get
     * @return The reply with matching ID
     */
    @GetMapping("/reply/{rpid}")
    public ReplyResponseDto getReplyById(@PathVariable int rpid) {
        Reply gotReply = replyService.getReply(rpid);
        return new ReplyResponseDto(gotReply); 
    }

    /**
     * Create reply
     * @param rid The ID of the review the reply is to be attached to.
     * @return The created reply 
     */
    @PostMapping("/review/{rid}/reply")
    public ReplyResponseDto createReply(@PathVariable int rid, @Valid @RequestBody ReplyRequestDto request) {
        Review review = reviewService.getReview(rid);
        Reply savedReply = replyService.createReply(request.getContent(), Date.valueOf(request.getDate()), review);
        return new ReplyResponseDto(savedReply);
    }

    /**
     * Edit reply by ID
     * @param rpid The ID of the reply to edit
     * @param request The reply request DTO with new attributes
     * @return The edited reply
     */
    @PutMapping("/reply/{rpid}")

    public ReplyResponseDto editReply(@PathVariable int rpid, @Valid @RequestBody ReplyRequestDto request) {
        Reply editedReply = replyService.editReply(rpid, request.getContent(), Date.valueOf(request.getDate()));
        return new ReplyResponseDto(editedReply);
    }

    /**
     * Get all replies to a review 
     * @param rid The review ID 
     * @return list of replies to the review in ReplyResponseDtos
     */
    @GetMapping("/review/{rid}/reply")
    public ReplyListDto getAllRepliesByReviewId(@PathVariable int rid) {
        List<Reply> repliesForReview = replyService.getRepliesByReview(rid);
        List<ReplyResponseDto> dtoList = new ArrayList<>();

        for (Reply reply : repliesForReview) {
            dtoList.add(new ReplyResponseDto(reply));
        }

        return new ReplyListDto(dtoList);
    }

    /**
     * Delete reply
     * @param rpid The reply ID
     */
    @DeleteMapping("/reply/{rpid}")
    public void deleteReplyById(@PathVariable int rpid) {
        replyService.deleteReply(rpid);
    }
}
