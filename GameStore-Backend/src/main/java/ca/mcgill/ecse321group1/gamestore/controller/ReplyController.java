package ca.mcgill.ecse321group1.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321group1.gamestore.model.Reply;
import ca.mcgill.ecse321group1.gamestore.service.ReplyService;

@RestController
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    /**
     * Get reply object by ID
     * @param rid
     */
    @GetMapping("/reply/{rid}")
    public Reply getReplyById(@PathVariable int rid) {
        return replyService.getReply(rid);
    }
    
}
