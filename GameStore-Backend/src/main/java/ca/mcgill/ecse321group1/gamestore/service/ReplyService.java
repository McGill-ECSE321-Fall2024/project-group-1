package ca.mcgill.ecse321group1.gamestore.service;

import ca.mcgill.ecse321group1.gamestore.model.Customer;
import ca.mcgill.ecse321group1.gamestore.model.Reply;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.repository.ReplyRepository;
import ca.mcgill.ecse321group1.gamestore.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository repo;

    /**Retrieves a Reply object based on lookup by id*/
    @Transactional
    public Reply getReply(int cid) {
        Reply c = repo.findReplyById(cid);
        if (c == null) {
            throw new IllegalArgumentException("There is no reply with ID " + cid + ".");
        }
        return c;
    }

    /**ONLY CALLED BY ReviewService. Creates a new Reply object with given attributes, and stores it into the databases*/
    @Transactional
    Reply initReply(String content, Date date, Review review) {
        if (content == null) content = "";

        if (review == null)
            throw new IllegalArgumentException("Reply must have Review!");

        Reply reply = new Reply();
        reply.setContent(content);
        reply.setDate(date);
        reply.setReview(review);
        return repo.save(reply);//no ID, gets set by this.;
    }

    /**Retrieves a Reply by ID, and modifies its attributes according to editRply's arguments before re-storing. Review it is attached to cannot be modified.*/
    @Transactional
    public Reply editReply(int id, String content, Date date) {
        if (content == null) content = "";
        Reply reply = getReply(id);
        reply.setContent(content);
        reply.setDate(date);
        return repo.save(reply);//no ID, gets set by this.
    }

    /**ONLY CALLED BY ReviewService. Deletes a Reply, permanently.*/
    @Transactional
    void deleteReply(int id) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant Reply!");
        repo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }
}